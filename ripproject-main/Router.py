import datetime
import random
import sys
import threading

from RIPHeader import RIPHeader
from RIPPacket import RIPPacket
from RIPRouteEntry import RIPRouteEntry
from ReadMessage import ReadMessage
from RouterConfigState import RouterConfigState
from RouterFSM import RouterFSM
from StateTransition import StateTransition
from WaitingState import WaitingState
from utils import print_string

LOCALHOST = '127.0.0.1'
MAX_TIMEOUT = 30
UPDATE_INTERVAL = 6
DELETE_EXPIRE = 18


class Router:
    """
    The RIP router which is responsible for managing routing tables and handling state transition.
    表示一个管理路由表并处理状态转换的 RIP 路由器。
    """

    def __init__(self, configuration_file):
        # 1. Initialise a new instance of Router class
        self.configuration_file = configuration_file
        self.route_state_change = False  # Set default state: not changed
        self.fsm = RouterFSM(self)
        self.readable_ports = []
        self.router_settings = {}
        self.routing_table = {}

        # 2. Initialize the states for RouterFSM.
        self.fsm.add_state("RouterConfigState", RouterConfigState(self.fsm))
        self.fsm.add_state("WaitingState", WaitingState(self.fsm))
        self.fsm.add_state("ReadMessage", ReadMessage(self.fsm))

        # 3. Initialize transitions for the RouterFSM.
        self.fsm.add_transition("toWaiting", StateTransition("WaitingState"))
        self.fsm.add_transition("toReadMessage", StateTransition("ReadMessage"))

        # 4. Set initial state of FSM.
        self.fsm.set_state("RouterConfigState")

    def execute(self):
        """Execute the FSM  of the router."""
        self.fsm.execute()

    def print_routing_table(self):
        """Printing the routing table as readable format for users."""
        print("-" * 75)
        print("|" + " " * 25 + "Routing Table (Router " + str(self.router_settings['id']) + ")" + " " * 24 + "|")
        print("-" * 75)

        print("|  Router ID  |  Metric  |  NextHop  |  Changed  |  ToDelete  |  Timeout  |")
        print("-" * 75)

        print(self.routing_table[self.router_settings['id']])  # Print current router
        print("-" * 75)

        # Print the other routers
        for entry in self.routing_table:
            if entry != self.router_settings["id"]:
                print(self.routing_table[entry])
                print("-" * 75)
        print("\n")

    def update_routing_table(self, packet):
        """Update the routing table according to the received packet."""
        for rte in packet.rip_rtes:
            if rte.addr == self.router_settings["id"]:
                continue

            best_route = self.routing_table.get(rte.addr)  # Get the best route in table for given addr
            rte.set_next_hop(packet.rip_header.src)  # Set the next hop to source router and calculate metric
            rte.metric = min(rte.metric + self.router_settings["outputs"][packet.rip_header.src]["metric"],
                             RIPRouteEntry.UNREACHABLE_METRIC)  # Set the mini metric

            # 1. Checking whether route exists in the routing table
            if not best_route:
                # 1.1 No route within the table, then add new router into table
                if rte.metric == RIPRouteEntry.UNREACHABLE_METRIC:
                    return  # Ignore the rtes of metric if it >= 16
                rte.changed = True
                self.route_state_change = True
                self.routing_table[rte.addr] = rte
                print("Rte added for router: " + str(rte.addr))
                return
            else:
                # 1.2 The route exists in the table, then update router for the table
                if rte.nextHop == best_route.nextHop:
                    if best_route.metric != rte.metric:
                        if (best_route.metric != RIPRouteEntry.UNREACHABLE_METRIC and
                                rte.metric >= rte.metric >= RIPRouteEntry.UNREACHABLE_METRIC):
                            best_route.metric = RIPRouteEntry.UNREACHABLE_METRIC
                            best_route.garbage = True
                            best_route.changed = True
                            self.route_state_change = True
                        else:
                            self.update_route(best_route, rte)  # Update the routing table
                    elif not best_route.garbage:
                        best_route.init_timeout()
                elif rte.metric < best_route.metric:
                    self.update_route(best_route, rte)  # Use new metric replace the old metric

    def update_route(self, best_route, rte):
        """Update an existing route entry with new route information provided by an incoming route entry."""
        # 1. Reset the timeout to extend the life of this route since it's actively updated
        best_route.init_timeout()
        best_route.garbage = False  # Ensure the route is no longer considered for garbage collection
        best_route.changed = True  # Indicate that this route has been updated

        # 2. Update the route's metric and next hop with the new information
        best_route.metric = rte.metric
        best_route.nextHop = rte.nextHop

        # 3. Indicate a state change in the routing table to trigger updates or logging
        self.route_state_change = True

        # 4. Log the update for debugging and monitoring purposes
        print_string("RTE for Router: " + str(rte.addr) +
                     " updated with metric=" + str(rte.metric) +
                     ", nextHop=" + str(rte.nextHop) + ".")

    def update(self, entries):
        """
        Send an update with changed route entries.
        发送已更改路由条目的更新。
        """
        # 1. Checking whether the settings exist
        if self.router_settings != {}:
            sock = list(self.router_settings["inputs"].values())[1]  # Get the list of all sockets and setup socket
            local_header = RIPHeader(router_id=self.router_settings["id"])

            for output in self.router_settings["outputs"]:
                split_horizon_entries = []
                for entry in entries:  # 2. Checking if any of the entries in the routing table had changed
                    if entry.nextHop != output:
                        # 2.1 Using split horizon rules: do not send entry back to the same router it was learned from
                        split_horizon_entries.append(entry)
                    else:
                        # 2.2 Using poison rule: poison the entry if the next hop == output port
                        poisoned_entry = RIPRouteEntry(rawdata=None, src_id=None, address=entry.addr,
                                                       nextHop=entry.nextHop, metric=RIPRouteEntry.UNREACHABLE_METRIC,
                                                       imported=entry.imported)
                        split_horizon_entries.append(poisoned_entry)
                # 3. Create a packet with the updated route entries and send through the output port
                packet = RIPPacket(rip_header=local_header, rip_rtes=split_horizon_entries)
                sock.sendto(packet.serialize(), (LOCALHOST, self.router_settings["outputs"][output]["port"]))
                print_string("Message sent to: " + str(output))

    def trigger_update(self):
        """
        Triggers an update to the routing table when routes have changed.
        当路由发生变化时触发路由表的更新。
        """
        route_changed = []  # 1. Store the changed routes
        for rte in self.routing_table.values():
            if rte.changed:
                route_changed.append(rte)  # 2. Add changed rtes into new list
                rte.changed = False

        self.route_state_change = False  # 3. Indicate all changes have been processed
        delay = random.randint(1, 5)  # 4. Send the update with random delay from 1 and 5 seconds
        threading.Timer(delay, self.update, [route_changed])

    def timer(self, function, param=None):
        """
        Start a periodic timer to call a specified function at random intervals within a range.
        启动一个定期计时器，在一个范围内的随机间隔调用指定的函数。
        """
        # 1. Calculate the random period if param is provided, otherwise use the default UPDATE_INTERVAL
        if param is not None:
            function(list(param.values()))
            period = UPDATE_INTERVAL * random.randrange(8, 12, 1) / 10
        else:
            period = UPDATE_INTERVAL
            function()

        # 2. Restart the timer at the end of each period
        threading.Timer(period, self.timer, [function, param]).start()

    def garbage_timer(self):
        """
        Checks for routes in the routing table that are marked as garbage and sets them for deletion
        if they have exceeded the allowed garbage hold time.
        检查路由表中标记为垃圾的路由，如果它们超过了允许的垃圾保持时间，则设置它们为删除状态。
        """
        print_string("Checking garbage timeout now.")

        if self.routing_table:
            for rte in self.routing_table.values():
                if rte.garbage and (datetime.datetime.now() - rte.timeout).total_seconds() >= DELETE_EXPIRE:
                    rte.marked_to_delete = True

    def garbage_collection(self):
        """
        Removes routes marked for deletion from the routing table and logs this cleanup.
        从路由表中删除标记为删除的路由，并记录此清理过程。
        """
        print_string("Collecting garbage now.")

        if self.routing_table != {}:
            deleted_routes = []  # Store the rtes which should be deleted
            for rte in self.routing_table.values():  # 1. Loop rte of routing table
                if rte.marked_to_delete:  # Default is None in class 6 RIPRouteEntry
                    deleted_routes.append(rte.addr)
                    print("Router: {} is removed from the routing table.".format(str(rte.addr)))
            for entry in deleted_routes:  # 2. Loop deleted list and remove them from routing table
                del self.routing_table[entry]
            self.print_routing_table()  # Print new/updated routing table

    def check_timeout(self):
        """
        Checking for routes in the routing table that have timed out and mark them as garbage, updating their metrics to
        indicate that they are unreachable.
        检查路由表中已超时的路由，并将它们标记为垃圾，更新它们的度量值以表示它们是不可达的。
        """
        print_string("Checking timeout now.")

        if self.routing_table:
            for rte in self.routing_table.values():
                # Check whether timeout is set and expired
                if rte.timeout and (datetime.datetime.now() - rte.timeout).total_seconds() >= 30:
                    rte.garbage = True
                    rte.changed = True
                    rte.metric = 16
                    rte.timeout = datetime.datetime.now()
                    self.route_state_change = True
                    self.print_routing_table()  # Print updated routing table
                    print_string("Router: {} times out.".format(rte.addr))

    def start_timer(self):
        """Start all necessary periodic operations."""
        self.timer(self.update, param=self.routing_table)
        self.timer(self.check_timeout)
        self.timer(self.garbage_timer)
        self.timer(self.garbage_collection)

    def main_loop(self):
        """Main execution loop for the router class."""
        while True:
            self.execute()


def main():
    """Main function to run the program."""

    if __name__ == "__main__":
        router = Router(str(sys.argv[-1]))
        router.start_timer()
        router.main_loop()


main()

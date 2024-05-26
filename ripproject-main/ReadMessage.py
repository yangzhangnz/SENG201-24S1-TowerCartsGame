from RIPPacket import RIPPacket
from RoutingState import RoutingState

from utils import print_string


class ReadMessage(RoutingState):
    """
    Class 5 Handles the reading of messages from input sockets and updates the routing table accordingly.
    处理从输入套接字读取消息并相应地更新路由表。
    """

    def __init__(self, fsm):
        super(ReadMessage, self).__init__(fsm)

    def enter(self):
        """Print a message indicating that the router is now in the message processing state."""
        print_string("Entering message processing state.")

    def execute(self):
        """
        Processes messages from input sockets, updates the routing table based on received data,
        and checks for necessary updates triggered by changes.
        从输入套接字处理消息，根据接收到的数据更新路由表，并检查由更改触发的必要更新。
        """
        # 1. For loop is used to receive message and create a RIP packet object
        for port in self.fsm.router.readable_ports:
            message, address = port.recvfrom(1024)  # Assuming 1024 is the buffer size
            packet = RIPPacket(message)  # RIPPacket is defined to parse the message
            self.fsm.router.update_routing_table(packet)   # Update routing table with received packet

        # 2. Check for changes in the routing table and trigger updates
        if self.fsm.router.route_state_change:
            self.fsm.router.trigger_update()  # Notice, every 1 - 6 seconds to trigger update

        # 3. Print the current routing table
        self.fsm.router.print_routing_table()

        # 4. Transition back to the waiting state
        self.fsm.to_transition("toWaiting")

    def exit(self):
        """Printing a message indicating that the state has been exited and message processing is complete."""
        print_string("Messages processed, exiting message processing state.")

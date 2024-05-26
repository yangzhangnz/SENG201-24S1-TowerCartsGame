import configparser
import socket
import sys

from RIPRouteEntry import RIPRouteEntry
from RoutingState import RoutingState
from utils import print_string
LOCALHOST = "127.0.0.1"


class RouterConfigState(RoutingState):
    """
    Class 3 is used to read and configure routing configuration & initialize router's inputs, outputs and routing table.
    这个类的目标是读取和设置配置文件以及初始化路由器的输入、输出、路由表。
    """

    def __init__(self, fsm):
        """
        Initialize a new instance of RouterConfigState with the given finite state machine (FSM).
        """
        super(RouterConfigState, self).__init__(fsm)

    def execute(self):
        """
        Configure the router based on the settings from the configuration file,
        and set up inputs and outputs, get router id and create routing table.
        """
        print_string("Now, you are configuring the router from the configuration file: '" +
                     self.fsm.router.configuration_file + "'")

        # Step 1: create a ConfigParser object and reads in the specified configuration file
        config = configparser.ConfigParser()
        config.read(self.fsm.router.configuration_file)
        # Step 2: router id, inputs, outputs, routing table
        self.get_router_id(config)
        self.setup_inputs(config)
        self.get_outputs(config)
        self.setup_routing_table()
        # Step 3: Print the routing table and transition to the next state in the FSM
        self.fsm.router.print_routing_table()
        self.fsm.to_transition("toWaiting")

    def get_router_id(self, config):
        """Read the router id from the configuration file, if it exists set it."""
        router_id = int(config["Settings"]["router-id"])
        if 1 <= router_id <= 64000:
            self.fsm.router.router_settings["id"] = router_id
        else:
            raise Exception("TThe router id must a positive integer between 1 and 64000.")

    def setup_inputs(self, config):
        """Creates input sockets for input ports in the configuration file."""
        inputs = []
        ports = config["Settings"]["input-ports"].split(", ")
        for port in ports:
            if 1024 <= int(port) <= 64000 and not int(port) in inputs:
                inputs.append(int(port))
            else:
                raise Exception("All port numbers are positive integers & between 1024 and 64000.")

        self.fsm.router.router_settings["inputs"] = {}

        # Create a socket for each input port and bind it to the specified host
        for port in inputs:
            # 1. Create socket for each input port
            try:
                self.fsm.router.router_settings["inputs"][port] = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
                print_string("Socket " + str(port) + " created.")
            except socket.error as msg:
                print("Failed to create socket: " + str(msg))
                sys.exit()
            # 2. Bind port to socket
            try:
                self.fsm.router.router_settings["inputs"][port].bind((LOCALHOST, port))
                print_string("Socket " + str(port) + " bind created.")
            except socket.error as msg2:
                print("Failed to bind socket to port" + str(port) + ": " + str(msg2))
                sys.exit()

    def get_outputs(self, config):
        """
        Configure the outputs based on the configuration, and returns a dictionary of output ports, related cost and
        destination router ID.
        """
        outputs = config["Settings"]["outputs"].split(", ")
        outputs = [i.split('-') for i in outputs]

        # Initialize the 'output-ports' key in the router settings dictionary. This dictionary will store the
        # configuration for each output port, including port number, cost, and destination router ID. It is set as an
        # empty dictionary to prepare for dynamic addition of output port settings during router configuration.
        self.fsm.router.router_settings["outputs"] = {}
        existing_ports = []
        for output in outputs:
            # 1. Check and store port
            if 1024 <= int(output[0]) <= 64000 and int(output[0]) not in existing_ports:
                is_valid_port = True
                existing_ports.append(int(output[0]))
            else:
                is_valid_port = False
            # 2. Check and store cost
            if 1 <= int(output[1]) < 16:
                is_valid_cost = True
            else:
                is_valid_cost = False
            # 3. Check and store router ID
            if 1 <= int(output[2]) <= 64000:
                is_valid_id = True
            else:
                is_valid_id = False
            # 4. All valid then store output to router setting dictionary
            if is_valid_port and is_valid_cost and is_valid_id:
                existing_ports.append(int(output[0]))
                self.fsm.router.router_settings["outputs"][int(output[2])] = \
                    {"metric": int(output[1]), "port": int(output[0])}
            else:
                raise Exception("The outputs are invalid.")

    def setup_routing_table(self):
        """Initialize the routing table with the router itself as the first entry."""
        self_id = self.fsm.router.router_settings["id"]
        self.fsm.router.routing_table[self_id] = RIPRouteEntry(address=self_id, nextHop=0, metric=0, imported=True)

    def exit(self):
        """
        Printing a message to indicate the completion of router configuration.
        指示路由器配置的完成。
        """
        print_string("Router configuration state is completed.")

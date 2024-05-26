import select

from RoutingState import RoutingState
from utils import print_string


class WaitingState(RoutingState):
    """
    Class 4 is used to represent the waiting state in the FSM for a router.
    Waiting for messages, if router receives message then change the router state to ReadMessage.
    这个类代表路由器 FSM 的等待状态。
    它在输入套接字上等待消息。当收到消息时，状态更改为 ReadMessage 状态。
    """

    def __init__(self, fsm):
        super(WaitingState, self).__init__(fsm)

    def enter(self):
        """Print a message to indicate that is in waiting mode."""
        print_string("Entering WaitingState. Waiting for messages now.")

    def execute(self):
        """Check for messages exist or not, if message is received, the state transitions to the ReadMessage state."""
        # 1. Check which input sockets have data ready to read.
        readable_sockets, _, _ = select.select(self.fsm.router.router_settings["inputs"].values(), [], [])

        if readable_sockets:
            # Store the list of sockets that are ready to read data in the router's readable_sockets attribute for
            # further processing.
            self.fsm.router.readable_ports = readable_sockets  # Call class 5 ReadMessage to store the sockets
            self.fsm.to_transition("toReadMessage")  # Call class 6 RouterFSM to change state

    def exit(self):
        """Print a message indicating that the state has been exited because message has been received."""
        print_string("Exiting WaitingState, message is received.")

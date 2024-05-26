class RoutingState:
    """
    Class 2 represents a state in the routing FSM.
    代表路由有限状态机（FSM）中的一个状态。
    """

    def __init__(self, fsm):
        """Initializes a new instance of the RoutingState class."""
        self.fsm = fsm  # fsm 参数代表这个状态所属的有限状态机实例

    def enter(self):
        """Enters the state of the routing FSM."""
        pass  # 这个方法将包含进入状态时需要执行的操作，目前是空的，需要后续填充

    def execute(self):
        """Executes the state of the routing FSM."""
        pass  # 这个方法将包含状态活动逻辑，例如处理事件或条件，目前是空的，需要后续填充

    def exit(self):
        """Exits the state of the routing FSM."""
        pass  # 这个方法将包含退出状态时需要执行的操作，目前是空的，需要后续填充

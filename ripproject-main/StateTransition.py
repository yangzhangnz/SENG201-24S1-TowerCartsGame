class StateTransition:
    """
    Class 1 represents a transition between states in an FSM.
    表示在有限状态机（FSM）中的状态转换。
    """

    def __init__(self, next_state):
        """Initialise a new instance of the StateTransition class."""
        self.next_state = next_state  # next_state 是状态转换后应该进入的下一个状态

    def execute(self):
        """Executes the state transition."""
        pass  # 当前方法是一个占位符，代表这里将会添加状态转换的实际逻辑

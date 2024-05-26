class RouterFSM:
    """
    Class 6 is used for managing the states and transitions of a router.
    用于管理路由器状态和转换的有限状态机。
    """

    def __init__(self, rip_router):
        """Initialize the RouterFSM class with a reference to a router."""
        self.router = rip_router
        self.states = {}
        self.transitions = {}
        self.current_state = None
        self.trans = None

    def add_state(self, state_name, state):
        """
        Add a new state to the FSM with the given name and state object representing a state of the router.
        向 FSM 添加一个新的状态，该状态具有给定的名称和表示路由器状态的状态对象。
        """
        self.states[state_name] = state

    def add_transition(self, trans_name, transition):
        """
        Add a new transition to the FSM with the given name and transition object.
        使用给定的名称和转换对象向 FSM 添加一个新的转换。
        """
        self.transitions[trans_name] = transition

    def set_state(self, state_name):
        """
        Set the current state of the FSM to the state with the given name.
        将 FSM 的当前状态设置为具有给定名称的状态。
        """
        self.current_state = self.states[state_name]

    def to_transition(self, to_trans):
        """
        Initiate a transition within the FSM to the transition with the given name.
        在 FSM 中启动到给定名称的转换。
        """
        self.trans = self.transitions[to_trans]

    def execute(self):
        """
        Execute the current state's logic and transition to the next state as specified by the current transition.
        执行当前状态的逻辑，并根据当前转换指定的内容转移到下一个状态。
        """
        self.current_state.execute()

        if self.trans:
            self.current_state.exit()
            self.trans.execute()
            self.set_state(self.trans.next_state)
            self.current_state.enter()
            self.trans = None

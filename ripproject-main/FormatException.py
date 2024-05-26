class FormatException(Exception):
    """
    Class 10 is used to raise when there is a problem with formatting of a RIP route entry.
    当RIP路由条目的格式存在问题时抛出的异常。
    """

    def __init__(self, message=""):
        """
        Initialize the FormatException object with the given message.
        使用给定的消息初始化 FormatException 对象。
        """
        # super().__init__(message)  # Calls the base class constructor with the message
        self.message = message

import struct


class RIPHeader:
    """
    Class 8 is used for the header of the RIP packet.
    表示路由信息协议（RIP）数据包头部的类。
    """

    # Define the binary format for RIP header
    FORMAT = "!BBH"  # !: order, BB: unsigned chars to store cmd and version, H: unsigned short to store router id
    VERSION = 2  # RIPv2
    TYPE_RESPONSE = 2  # 在RIP协议中，2通常表示这是一个响应类型的数据包，相对于请求类型的数据包。
    SIZE = struct.calcsize(FORMAT)  # SIZE 计算由 FORMAT 指定的结构的大小（以字节为单位）。这对于在网络通信中解析和构造数据包特别重要，因为它确保了在读取或写入数据时使用正确的字节长度。

    def __init__(self, rawdata=None, router_id=None):
        """
        Initializes the RIPHeader object either from raw network data or with a specified router ID for outgoing packets
        根据原始网络数据或指定的路由器ID（用于发送包）初始化RIPHeader对象
        """
        self.packet_data = None
        if rawdata:
            self._init_from_network(rawdata)  # The raw bytes received from the network
        elif router_id is not None:
            self._init_from_host(router_id)  # The ID of the router is used when creating a header for sending
        else:
            raise ValueError

    def __repr__(self):
        """Returns a string representation of the RIPHeader object."""
        return "RIP Header (cmd = {}, ver = {}, src = {})".format(self.cmd, self.ver, self.src)

    def _init_from_network(self, rawdata):
        """
        Initialize the header from raw network data by unpacking it according to the predefined format.
        根据预定义的格式解包从网络接收的原始数据来初始化头部。
        """
        if len(rawdata) < self.SIZE:
            raise ValueError("The rawdata is too short!")
        header = struct.unpack(self.FORMAT, rawdata)

        self.cmd = header[0]  # Command
        self.ver = header[1]  # Vers
        self.src = header[2]  # Router ID

    def _init_from_host(self, router_id):
        """
        Initialize the header for an outgoing packet with the given router ID.
        使用给定的路由器 ID 初始化要发送的数据包的头部。
        """
        self.cmd = self.TYPE_RESPONSE  # Set the command type to response
        self.ver = self.VERSION  # Set the version of the RIP
        self.src = router_id  # Set the source router ID

    def serialize(self):
        """
        Serialize the header into bytes suitable for network transmission.
        将头部序列化成适合网络传输的字节。
        """
        return struct.pack(self.FORMAT, self.cmd, self.ver, self.src)

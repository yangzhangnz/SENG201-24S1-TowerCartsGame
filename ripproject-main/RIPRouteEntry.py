import datetime
import struct

from FormatException import FormatException

AF_INET = 2


class RIPRouteEntry:
    """
    Class 9 is used to represent a single Routing RIP route entry
    表示单个路由信息协议（RIP）路由条目。
    """

    # Format str for packing RIP rtes
    FORMAT = "!HHIII"  # !: order, HH: address family identifier and router tag, III: ip address + nextHop + metric
    UNREACHABLE_METRIC = 16  # 表示目的地不可达的最大度量值。
    MINI_METRIC = 0  # 定义了度量值的最小可能值，通常用于表示连接的路由。
    SIZE = struct.calcsize(FORMAT)  # 计算基于 FORMAT 指定的格式的数据结构将占用多少字节。这对于处理网络数据尤其重要，因为需要确保在网络传输中发送和接收正确的数据量。

    def __init__(self, rawdata=None, src_id=None, address=None, nextHop=None, metric=None, imported=False):
        """
        Constructor method for RIPRouteEntry class.

        Initializes a RIPRouteEntry either from raw data or by specifying attributes for outgoing packets.
        通过原始数据或指定属性初始化 RIP 路由条目，用于出站数据包。

        :param rawdata: byes - raw data from network about the RIP rtes. 从网络接收的表示 RIP 路由条目的原始数据。
        :param src_id: int - source IP address of the RIP rtes. RIP 路由条目的源 ID。
        :param address: int - dst IP address of the RIP rtes. RIP 路由条目的目的 IP 地址。
        :param nextHop: int - RIP rte's ip address nextHop. RIP 路由条目的下一跳 IP 地址。
        :param metric: int - RIP rte's metric. 0-15 reachable, 16 unreachable. RIP路由条目的度量值，从0到15，其中16表示不可达。
        :param imported: boole - whether RIP rte is imported from another routing protocol. 是否从其他路由协议导入的RIP路由条目。
        """
        self.nextHop = None
        self.garbage = None
        self.timeout = None
        self.marked_to_delete = None
        self.imported = imported  # # Flag to indicate if this entry was imported from another router
        self.changed = False  # Track if the route has changed
        self.init_timeout()  # Initialize a timeout for this route entry

        if rawdata is not None and src_id is not None:
            self._init_from_network(rawdata, src_id)
        elif address is not None and nextHop is not None and metric is not None:
            self._init_from_host(address, nextHop, metric)
        else:
            raise ValueError("The arguments are invalid.")

    def __repr__(self):
        """
        Formats string of rtes to debug.
        提供路由条目的格式化字符串表示，用于调试。
        """
        # Determine timeout
        template = "|{:^13}|{:^10}|{:^11}|{:^11}|{:^12}|{:^11}|"
        if self.timeout is None:
            return template.format(self.addr, self.metric, self.nextHop, self.changed, self.garbage, str(self.timeout))
        else:
            timeout = (datetime.datetime.now() - self.timeout).total_seconds()
            return template.format(self.addr, self.metric, self.nextHop, self.changed, self.garbage, round(timeout, 1))

    def _init_from_network(self, rawdata, src_id):
        """Initialize RIPRouteEntry object from data received from the network."""
        rte = struct.unpack(self.FORMAT, rawdata)
        self.afi = rte[0]  # Address Family Identifier
        self.tag = rte[1]  # Route Tag
        self.addr = rte[2]  # IP address
        self.set_next_hop(rte[3])  # Next Hop
        self.metric = rte[4]  # Metric

        # 1. Determine the appropriate next hop address
        if self.nextHop == 0:
            self.nextHop = src_id

        # 2. Check whether the metric value is below the minimum or above the maximum allowed values
        if not (self.MINI_METRIC <= self.metric <= self.UNREACHABLE_METRIC):
            raise FormatException

    def _init_from_host(self, address, nextHop, metric):
        """Initialize RIPRouteEntry object from data received from a host."""
        self.afi = AF_INET
        self.tag = 0
        self.addr = address
        self.nextHop = nextHop
        self.metric = metric

    def init_timeout(self):
        """
        Initializes or resets the timeout for this route entry.
        初始化或重置此路由条目的超时，如果未更新，则标记该条目以便删除。
        """
        if self.imported:
            self.timeout = None
        else:
            self.timeout = datetime.datetime.now()
        self.garbage = False
        self.marked_to_delete = False

    def set_next_hop(self, nextHop):
        """设置此路由条目的下一跳地址。"""
        self.nextHop = nextHop

    def __eq__(self, other):
        """Checking the rtes are equal, by comparing AFI, tag, IP, nextHop and metric."""
        return ((self.afi, self.addr, self.tag, self.nextHop, self.metric) ==
                (other.afi, other.addr, other.tag, other.nextHop, other.metric))

    def serialize(self):
        """将路由条目序列化为网络传输的字节字符串。"""
        return struct.pack(self.FORMAT, self.afi, self.tag, self.addr, self.nextHop, self.metric)

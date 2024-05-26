from FormatException import FormatException
from RIPHeader import RIPHeader
from RIPRouteEntry import RIPRouteEntry


class RIPPacket:
    """
    Class 7 is used to represent a RIP packet to process RIP packets creating and parsing.
    表示一个路由信息协议（RIP）数据包，处理数据包的创建和解析。
    """

    def __init__(self, rip_data=None, rip_header=None, rip_rtes=None):
        """
        Initialize the RIPPacket object.
        初始化 RIPPacket 对象。
        1. If raw data is provided, the packet is parsed from the network.
           如果提供了原始数据，则从网络解析数据包。
        2. If header and rtes are provided, the packet is assembled for sending.
           如果提供了头部和路由项，数据包将被组装以便发送。
        :param rip_data: raw bytes of the RIP packet received from the network.
        从网络接收到的RIP数据包的原始字节。
        :param rip_header: RIPHeader obj represents the header of the RIP packet.
        表示RIP数据包头部的RIPHeader对象。
        :param rip_rtes: a list of RTE objects representing the rtes of RIP packet.
        表示RIP数据包路由条目的RIPRouteEntry对象列表。
        3. 如果没有提供‘rip_data’或者（‘rip_header’和‘rip_rtes’），则抛出。
        """
        if rip_data is not None:
            self._parse_from_network(rip_data)
        elif rip_header is not None and rip_rtes is not None:
            self._assemble_from_components(rip_header, rip_rtes)
        else:
            raise ValueError("You must provide either rip_data or rip_header or rip_rtes.")

    def __repr__(self):
        """
        Return a string representation of the RIPPacket object.
        返回 RIPPacket 对象的字符串表示。
        """
        return "RIPPacket: Command {}, Ver. {}, number of RTEs {}.".format(self.rip_header.cmd, self.rip_header.ver,
                                                                           len(self.rip_rtes))

    def _parse_from_network(self, rip_data):
        """
        Parse the RIPPacket data from the network, then check the format and extract.
        从网络接收的原始字节解析 RIPPacket，验证格式并提取组件。
        :param rip_data:raw RIPPacket bytes data from the network. 从网络接收的RIP数据包的原始字节。 如果数据包格式错误，则抛出。
        """
        # 1. Check packet validation
        if len(rip_data) < RIPHeader.SIZE:
            raise FormatException("Your packet's length is too short.")
        if (len(rip_data) - RIPHeader.SIZE) % RIPRouteEntry.SIZE:
            raise FormatException("Your packet in wrong format.")

        # 2. 从原始的网络数据中解析出 RIP 数据包的结构组成部分，包括数据包的头部和路由条目。
        self.rip_header = RIPHeader(rip_data[0:RIPHeader.SIZE])  # 2.1 初始化头部
        self.rip_rtes = []  # 2.2 初始化路由条目列表
        start_index = RIPHeader.SIZE
        end_index = RIPHeader.SIZE + RIPRouteEntry.SIZE
        num_rtes = int((len(rip_data) - RIPHeader.SIZE) / RIPRouteEntry.SIZE)

        for i in range(num_rtes):
            self.rip_rtes.append(RIPRouteEntry(rawdata=rip_data[start_index:end_index], src_id=self.rip_header.src))
            start_index += RIPRouteEntry.SIZE
            end_index += RIPRouteEntry.SIZE

    def _assemble_from_components(self, rip_header, rip_rtes):
        """从头部对象和路由条目列表组装 RIPPacket，检查正确的版本。"""
        if rip_header.ver != 2:
            raise ValueError("Only RIP v2 supported.")
        self.rip_header = rip_header
        self.rip_rtes = rip_rtes

    def serialize(self):
        """
        Serialize the RIPPacket for network transmission.
        This involves converting the header and all route entries into bytes.
        序列化 RIPPacket 以进行网络传输。 这包括将头部和所有路由条目转换成字节序列。
        """
        packet_data = self.rip_header.serialize()  # Serialize the header part of the packet
        for rte in self.rip_rtes:  # Iterate through all route entries in the packet
            packet_data += rte.serialize()  # Serialize each route entry and append it to the header bytes
        return packet_data

package me.liuli.atb.checks

import cn.nukkit.network.protocol.DataPacket

abstract class PacketCheck : CheckBase() {
    // packet from client (RECEIVE)
    open fun handleClient(packet: DataPacket) {
    }

    // packet from server (SEND)
    open fun handleServer(packet: DataPacket) {

    }
}
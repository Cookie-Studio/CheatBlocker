package me.liuli.cb.checks

import cn.nukkit.Player
import cn.nukkit.network.protocol.DataPacket

abstract class PacketCheck(type: CheckType, subCheck: String) : CheckBase(type, subCheck) {
    // packet from client (RECEIVE)
    open fun handleClient(packet: DataPacket, player: Player) {
    }

    // packet from server (SEND)
    open fun handleServer(packet: DataPacket, player: Player) {

    }
}
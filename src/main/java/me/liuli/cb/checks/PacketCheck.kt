package me.liuli.cb.checks

import cn.nukkit.network.protocol.DataPacket
import me.liuli.cb.data.PlayerData

abstract class PacketCheck(type: CheckType, subCheck: String, flyFlag: Boolean) : Check(type, subCheck, flyFlag) {
    // packet from client (RECEIVE)
    open fun handleClient(packet: DataPacket, data: PlayerData) {
    }

    // packet from server (SEND)
    open fun handleServer(packet: DataPacket, data: PlayerData) {

    }
}
package me.liuli.cb.checks

import cn.nukkit.network.protocol.MovePlayerPacket
import me.liuli.cb.data.PlayerData

abstract class MoveCheck(type: CheckType, subCheck: String, flyFlag: Boolean) : Check(type, subCheck, flyFlag) {
    // Receive client-side move packet
    open fun handleCheck(packet: MovePlayerPacket, data: PlayerData) {

    }
}
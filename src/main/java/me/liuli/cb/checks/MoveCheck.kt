package me.liuli.cb.checks

import cn.nukkit.Player
import cn.nukkit.network.protocol.MovePlayerPacket

abstract class MoveCheck(type: CheckType, subCheck: String) : CheckBase(type, subCheck) {
    // Receive client-side move packet
    open fun handleCheck(packet: MovePlayerPacket, player: Player) {

    }
}
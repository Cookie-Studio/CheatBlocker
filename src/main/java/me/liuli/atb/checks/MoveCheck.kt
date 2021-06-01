package me.liuli.atb.checks

import cn.nukkit.network.protocol.MovePlayerPacket

abstract class MoveCheck : CheckBase() {
    // Receive client-side move packet
    open fun handleCheck(packet: MovePlayerPacket) {

    }
}
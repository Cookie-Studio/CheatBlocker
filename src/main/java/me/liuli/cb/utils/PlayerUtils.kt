package me.liuli.cb.utils

import cn.nukkit.Player
import cn.nukkit.network.protocol.DisconnectPacket
import java.util.*

object PlayerUtils {
    val blockList = mutableListOf<UUID>()

    fun kickPlayerUsePacket(player: Player, message: String) {
        blockList.add(player.uniqueId)
        val packet = DisconnectPacket()
        packet.hideDisconnectionScreen = false
        packet.message = message
        player.dataPacket(packet)

        Timer().schedule(object : TimerTask() {
            override fun run() {
                if (player.isOnline) {
                    player.kick(message)
                }
            }
        }, 200L)
    }
}
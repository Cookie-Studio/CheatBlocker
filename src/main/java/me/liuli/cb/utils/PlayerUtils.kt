package me.liuli.cb.utils

import cn.nukkit.Player
import cn.nukkit.network.protocol.DisconnectPacket
import java.util.*

object PlayerUtils {
    fun kickPlayerUsePacket(player: Player, message: String){
        Timer().schedule(object : TimerTask(){
            override fun run() {
                val packet= DisconnectPacket()
                packet.hideDisconnectionScreen=false
                packet.message=message
                player.dataPacket(packet)
            }
        },300L)

        Timer().schedule(object : TimerTask(){
            override fun run() {
                player.close(message)
            }
        },700L)
    }
}
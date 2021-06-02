package me.liuli.cb.listen

import cn.nukkit.event.EventHandler
import cn.nukkit.event.EventPriority
import cn.nukkit.event.Listener
import cn.nukkit.event.server.DataPacketReceiveEvent
import cn.nukkit.event.server.DataPacketSendEvent
import me.liuli.cb.CheatBlocker

class PacketListener : Listener {
    private val checkManager = CheatBlocker.getInstance().checkManager

    @EventHandler(priority = EventPriority.HIGHEST)
    fun onPacketReceive(event: DataPacketReceiveEvent) {
        event.player ?: return
        if (checkManager.handlePacketIn(event)) {
            event.setCancelled()
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    fun onPacketSend(event: DataPacketSendEvent) {
        event.player ?: return
        if (checkManager.handlePacketOut(event)) {
            event.setCancelled()
        }
    }
}
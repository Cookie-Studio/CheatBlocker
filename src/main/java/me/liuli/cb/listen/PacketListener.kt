package me.liuli.cb.listen

import cn.nukkit.event.EventHandler
import cn.nukkit.event.EventPriority
import cn.nukkit.event.Listener
import cn.nukkit.event.player.PlayerLoginEvent
import cn.nukkit.event.player.PlayerQuitEvent
import cn.nukkit.event.server.DataPacketReceiveEvent
import cn.nukkit.event.server.DataPacketSendEvent
import cn.nukkit.network.protocol.DisconnectPacket
import me.liuli.cb.CheatBlocker
import me.liuli.cb.utils.PlayerUtils

class PacketListener : Listener {
    private val checkManager = CheatBlocker.getInstance().checkManager
    private val punishManager = CheatBlocker.getInstance().punishManager

    // ban system
    @EventHandler(priority = EventPriority.HIGHEST)
    fun onPlayerLogin(event: PlayerLoginEvent) {
        punishManager.checkPlayer(event.player)
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    fun onPlayerQuit(event: PlayerQuitEvent) {
        PlayerUtils.blockList.remove(event.player.uniqueId)
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    fun onPacketReceive(event: DataPacketReceiveEvent) {
        event.player ?: return

        if (PlayerUtils.blockList.contains(event.player.uniqueId)) {
            event.setCancelled()
            return
        }

        if (checkManager.handlePacketIn(event)) {
            event.setCancelled()
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    fun onPacketSend(event: DataPacketSendEvent) {
        event.player ?: return

        if (PlayerUtils.blockList.contains(event.player.uniqueId) && event.packet !is DisconnectPacket) {
            event.setCancelled()
            return
        }

        if (checkManager.handlePacketOut(event)) {
            event.setCancelled()
        }
    }
}
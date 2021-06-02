package me.liuli.cb.listen

import cn.nukkit.event.EventHandler
import cn.nukkit.event.EventPriority
import cn.nukkit.event.Listener
import cn.nukkit.event.player.PlayerJoinEvent
import cn.nukkit.event.player.PlayerQuitEvent
import me.liuli.cb.CheatBlocker

class PlayerListener : Listener {
    private val checkManager= CheatBlocker.getInstance().checkManager

    @EventHandler(priority = EventPriority.HIGHEST)
    fun onPlayerJoin(event: PlayerJoinEvent){
        checkManager.handlePlayerJoin(event)
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    fun onPlayerQuit(event: PlayerQuitEvent){
        checkManager.handlePlayerLeave(event)
    }
}
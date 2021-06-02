package me.liuli.cb.checks

import cn.nukkit.Player
import me.liuli.cb.CheatBlocker
import me.liuli.cb.data.PlayerData

abstract class Check(val type: CheckType, val subCheck: String, val flyFlag: Boolean) {
    private val config=CheatBlocker.getInstance().configManager.checkConfig.config.getSection(type.yamlName)
    private val subConfig=config.getSection(subCheck)

    val addVL=subConfig.getDouble("addVL").toFloat()

    // called every tick
    open fun onTick() {

    }

    // called when a player join game
    open fun onPlayerJoin(data: PlayerData) {

    }

    // called when a player leave game
    open fun onPlayerLeave(data: PlayerData) {

    }
}
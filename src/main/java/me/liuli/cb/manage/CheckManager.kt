package me.liuli.cb.manage

import cn.nukkit.event.player.PlayerJoinEvent
import cn.nukkit.event.player.PlayerQuitEvent
import cn.nukkit.event.server.DataPacketReceiveEvent
import cn.nukkit.event.server.DataPacketSendEvent
import me.liuli.cb.CheatBlocker
import me.liuli.cb.checks.Check
import me.liuli.cb.checks.MoveCheck
import me.liuli.cb.checks.PacketCheck
import me.liuli.cb.checks.impl.move.MoveY1

class CheckManager {
    private val moveChecks = mutableListOf<MoveCheck>()
    private val packetChecks = mutableListOf<PacketCheck>()

    private val playerManager = CheatBlocker.getInstance().playerManager

    init {
        registerChecks(
            MoveY1::class.java
        )
    }

    @SafeVarargs
    private fun registerChecks(vararg checks: Class<out Check>) {
        checks.forEach(this::registerCheck)
    }

    // used in registerChecks
    private fun registerCheck(checkClass: Class<out Check>) {
        registerCheck(checkClass.getDeclaredConstructor().newInstance())
    }

    private fun registerCheck(check: Check) {
        if (check is MoveCheck) {
            moveChecks.add(check)
        } else if (check is PacketCheck) {
            packetChecks.add(check)
        } else {
            throw IllegalStateException("Illegal check type: ${check.javaClass.name}")
        }
    }

    fun handlePlayerJoin(event: PlayerJoinEvent) {
        val data = playerManager.addPlayer(event.player)
        moveChecks.forEach { it.onPlayerJoin(data) }
        packetChecks.forEach { it.onPlayerJoin(data) }
    }

    fun handlePlayerLeave(event: PlayerQuitEvent) {
        val data = playerManager.getPlayer(event.player) ?: return
        moveChecks.forEach { it.onPlayerLeave(data) }
        packetChecks.forEach { it.onPlayerLeave(data) }
        playerManager.removePlayer(event.player)
    }

    fun handlePacketIn(event: DataPacketReceiveEvent): Boolean {
        return false
    }

    fun handlePacketOut(event: DataPacketSendEvent): Boolean {
        return false
    }

    fun handleUpdate() {
        moveChecks.forEach { it.onTick() }
        packetChecks.forEach { it.onTick() }
    }
}
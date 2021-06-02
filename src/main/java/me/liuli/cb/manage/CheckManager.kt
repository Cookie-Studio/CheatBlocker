package me.liuli.cb.manage

import cn.nukkit.event.player.PlayerJoinEvent
import cn.nukkit.event.player.PlayerQuitEvent
import cn.nukkit.event.server.DataPacketReceiveEvent
import cn.nukkit.event.server.DataPacketSendEvent
import me.liuli.cb.checks.CheckBase
import me.liuli.cb.checks.MoveCheck
import me.liuli.cb.checks.PacketCheck
import me.liuli.cb.checks.impl.move.MoveY1

class CheckManager {
    private val moveChecks=mutableListOf<MoveCheck>()
    private val packetChecks=mutableListOf<PacketCheck>()

    init {
        registerChecks(
            MoveY1::class.java
        )
    }

    @SafeVarargs
    private fun registerChecks(vararg checks: Class<out CheckBase>){
        checks.forEach(this::registerCheck)
    }

    // used in registerChecks
    private fun registerCheck(checkClass: Class<out CheckBase>) {
        registerCheck(checkClass.newInstance())
    }

    private fun registerCheck(check: CheckBase) {
        if(check is MoveCheck){
            moveChecks.add(check)
        }else if(check is PacketCheck){
            packetChecks.add(check)
        }else{
            throw IllegalStateException("Illegal check type: ${check.javaClass.name}")
        }
    }

    fun handlePlayerJoin(event: PlayerJoinEvent){

    }

    fun handlePlayerLeave(event: PlayerQuitEvent){

    }

    fun handlePacketIn(event: DataPacketReceiveEvent):Boolean{
        return false
    }

    fun handlePacketOut(event: DataPacketSendEvent):Boolean{
        return false
    }

    fun handleUpdate(){

    }
}
package me.liuli.cb.data

import cn.nukkit.Player
import cn.nukkit.math.Vector3
import cn.nukkit.math.Vector3f
import me.liuli.cb.checks.Check
import me.liuli.cb.checks.CheckType

class PlayerData(val player: Player) {
    val violations=HashMap<CheckType,Float>()

    val lastGroundPos=Vector3f(0F,0F,0F)

    fun doFlag(check: Check, multipler: Float, mustFlag: Boolean){
        var vl=violations.getOrDefault(check.type,0F)
        vl+=check.addVL*multipler
        violations[check.type]=vl
//        if(mustFlag||ck)
    }
}
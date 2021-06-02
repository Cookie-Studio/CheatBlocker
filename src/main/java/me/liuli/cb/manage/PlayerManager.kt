package me.liuli.cb.manage

import cn.nukkit.Player
import me.liuli.cb.data.PlayerData
import java.util.*
import kotlin.collections.HashMap

class PlayerManager {
    val players=HashMap<UUID,PlayerData>()

    fun addPlayer(player: Player):PlayerData{
        val data=PlayerData(player)
        players[player.uniqueId] = data
        return data
    }

    fun getPlayer(player: Player):PlayerData?{
        return players[player.uniqueId]
    }

    fun removePlayer(player: Player){
        players.remove(player.uniqueId)
    }
}
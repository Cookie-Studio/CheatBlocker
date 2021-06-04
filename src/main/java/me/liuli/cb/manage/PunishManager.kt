package me.liuli.cb.manage

import cn.nukkit.Player
import cn.nukkit.Server
import cn.nukkit.utils.Config
import me.liuli.cb.CheatBlocker
import me.liuli.cb.utils.AntiCheatCommandSender
import me.liuli.cb.utils.PlayerUtils
import me.liuli.cb.utils.StringUtils
import java.io.File
import java.text.DateFormat
import java.util.*

class PunishManager {
    private val punishConfig=CheatBlocker.getInstance().configManager.punishConfig
    private val logger=CheatBlocker.getTheLogger()

    private val baseFolder: File
    private val playersFolder: File
    private val ipsFolder: File

    init {
        baseFolder=File(CheatBlocker.getInstance().dataFolder,"punish")
        playersFolder=File(baseFolder,"players")
        ipsFolder=File(baseFolder,"ips")
    }

    fun kickPlayer(name: String,reason: String=punishConfig.kickDefaultReason,operator: String="Anticheat"):Boolean{
        val player=Server.getInstance().getPlayer(name) ?: return false
        val data=getOrCreateData(name)
        data["kicked_times"]=(data["kicked_times"] as Int)+1
        data.save()
        // check can ban
        var ban=false
        if((data["kicked_times"] as Int) >= punishConfig.threshold){
//            banPlayer(data,reason = reason,operator = operator)
            val server=Server.getInstance()
            punishConfig.banCommands.forEach{
                server.dispatchCommand(AntiCheatCommandSender(operator),it.replace("%player%",player.name)
                    .replace("%playerIp%",player.address)
                    .replace("%time_ms%",punishConfig.banTime.toString())
                    .replace("%time%",punishConfig.expirationTime.toString())
                    .replace("%time_unit%",punishConfig.expirationTimeUnit))
            }
            // reload data after command modify
            data.reload()
            ban=true
            checkPlayer(player,data)
            logger.warning("${player.name} banned (Reached ban VL)")
        }

        if(!ban){
            var str=""
            punishConfig.kickUI.forEach{
                str+=it.replace("%player%",player.name)
                    .replace("%playerIp%",player.address)
                    .replace("%operator%",operator)
                    .replace("%reason%",reason)
                str+="\n"
            }
            PlayerUtils.kickPlayerUsePacket(player,str)
            logger.warning("${player.name} kicked. Ban VL ${data["kicked_times"] as Int}/${punishConfig.threshold}")
        }

        return true
    }

    fun checkPlayer(player: Player):Boolean{
        return checkPlayer(player,getOrCreateData(player.name.toLowerCase()))
    }

    fun checkPlayer(player: Player, data: Config):Boolean{
        // record ip
        data["ip"]=player.address
        data.save()
        if(data.getBoolean("banned")){
            val expired=data["expired"] as Long
            if(expired==-1L||expired>System.currentTimeMillis()) {
                var str = ""
                val expiredTime=DateFormat.getDateTimeInstance().format(Date(expired))
                val expiredLast=StringUtils.t2s((expired-System.currentTimeMillis())/1000L)
                punishConfig.banUI.forEach {
                    str += it.replace("%player%", player.name)
                        .replace("%playerIp%", player.address)
                        .replace("%operator%", data["ban_operator"] as String)
                        .replace("%reason%", data["ban_reason"] as String)
                        .replace("%expired_last%", expiredLast)
                        .replace("%expired_time%", expiredTime)
                    str += "\n"
                }
                PlayerUtils.kickPlayerUsePacket(player, str)
                return true
            }else{
                data["banned"]=false
                data.save()
            }
        }
        // ipban
        val dataFile=File(ipsFolder,"${player.address}.yml")
        if(dataFile.exists()){
            val ipData=Config(dataFile,Config.YAML)
            val expired=ipData["expired"] as Long
            if(expired==-1L||expired>System.currentTimeMillis()) {
                var str = ""
                val expiredTime=DateFormat.getDateTimeInstance().format(Date(expired))
                val expiredLast=StringUtils.t2s((expired-System.currentTimeMillis())/1000L)
                punishConfig.banIpUI.forEach {
                    str += it.replace("%player%", player.name)
                        .replace("%playerIp%", player.address)
                        .replace("%operator%", ipData["ban_operator"] as String)
                        .replace("%reason%", ipData["ban_reason"] as String)
                        .replace("%expired_last%", expiredLast)
                        .replace("%expired_time%", expiredTime)
                    str += "\n"
                }
                // chain ban
                if(punishConfig.banIPChain){
                    banPlayer(data,reason = punishConfig.banIPChainReason,time = expired,operator = (ipData["ban_operator"] as String))
                    checkPlayer(player, data)
                    logger.warning("${player.name} chain banned. IP: ${player.address}")
                    return true
                }
                PlayerUtils.kickPlayerUsePacket(player, str)
                return true
            }else{
                dataFile.delete()
            }
        }
        return false
    }

    fun banPlayer(name: String, reason: String=punishConfig.banDefaultReason, time: Long=punishConfig.banTime,operator: String="Anticheat"){
        banPlayer(getOrCreateData(name),reason, time, operator)
    }

    fun banPlayer(data: Config, reason: String=punishConfig.banDefaultReason, time: Long=punishConfig.banTime,operator: String="Anticheat"){
        data["expired"]=if(time==-1L){
            -1L
        }else{
            System.currentTimeMillis()+time
        }
        data["kicked_times"]=0
        data["ban_operator"]=operator
        data["ban_reason"]=reason
        data["banned"]=true
        data.save()
    }

    fun isBannedPlayer(name: String):Boolean{
        val data=getOrCreateData(name)
        return data["banned"] as Boolean
    }

    fun getOrCreateData(name: String):Config{
        val dataFile=File(playersFolder,"${name.toLowerCase()}.yml")
        try {
            if(dataFile.exists()){
                return Config(dataFile,Config.YAML)
            }
        }catch (t: Throwable){
            t.printStackTrace()
        }
        // file not exists or read failed
        if(dataFile.exists()){
            dataFile.delete()
        }
        val data=Config(dataFile,Config.YAML)
        data["banned"]=false
        data["expired"]=0L
        data["kicked_times"]=0

        return data
    }

    fun dataExists(name: String):Boolean{
        return File(playersFolder,"${name.toLowerCase()}.yml").exists()
    }

    fun unbanPlayer(name: String):Int{
        val dataFile=File(playersFolder,"${name.toLowerCase()}.yml")
        if(!dataFile.exists()){
            return 0 // player not exists
        }
        val data=getOrCreateData(name)
        if(data.getBoolean("banned")){
            data["banned"]=false
            data.save()
            return 2 // unban success
        }
        return 1 // not banned
    }

    fun banIP(ip: String, reason: String=punishConfig.banDefaultReason, time: Long=punishConfig.banTime,operator: String="Anticheat"){
        val dataFile=File(ipsFolder,"${ip.toLowerCase()}.yml")
        val data=Config(dataFile,Config.YAML)
        data["expired"]=if(time==-1L){
            -1L
        }else{
            System.currentTimeMillis()+time
        }
        data["ban_operator"]=operator
        data["ban_reason"]=reason
        data.save()
    }

    fun unbanIP(ip: String):Boolean{
        val dataFile=File(ipsFolder,"${ip.toLowerCase()}.yml")
        if(!dataFile.exists()){
            return false
        }
        dataFile.delete()
        return true
    }
}
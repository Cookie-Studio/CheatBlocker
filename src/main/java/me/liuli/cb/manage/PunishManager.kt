package me.liuli.cb.manage

import cn.nukkit.utils.Config
import me.liuli.cb.CheatBlocker
import java.io.File

class PunishManager {
    private val folder: File

    init {
        folder=File(CheatBlocker.getInstance().dataFolder,"punish")
    }

    fun warnPlayer(name: String){
        val data=getOrCreateData(name)
        data["kicked_times"]=(data["kicked_times"] as Int)+1
        // check can ban

        // finally save data and check can ban
        data.save()
    }

    fun isBanned(name: String):Boolean{
        val data=getOrCreateData(name)
        return data["banned"] as Boolean
    }

    fun getOrCreateData(name: String):Config{
        val dataFile=File(folder,"${name.toLowerCase()}.yml")
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
        data["ban_until"]=0L
        data["kicked_times"]=0

        return data
    }
}
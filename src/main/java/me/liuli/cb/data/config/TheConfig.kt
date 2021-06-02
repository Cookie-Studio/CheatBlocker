package me.liuli.cb.data.config

import cn.nukkit.utils.Config
import me.liuli.cb.CheatBlocker
import me.liuli.cb.utils.FileUtils
import java.io.File

open class TheConfig(configName: String) {
    val config: Config

    init {
        // check file exists
        val configFile=File(CheatBlocker.getInstance().dataFolder,configName)
        if(!configFile.exists()){
            FileUtils.writeFile(configFile,FileUtils.getTextFromResource(configName))
        }
        config=Config(configFile, Config.YAML)

        // read data to vars
        val fields=this.javaClass.fields
        for (field in fields){
            field.annotations.forEach {
                if(it is ConfigField){
                    field.set(this, config.get(it.yamlSection))
                }
            }
        }
    }
}

annotation class ConfigField(val yamlSection: String)
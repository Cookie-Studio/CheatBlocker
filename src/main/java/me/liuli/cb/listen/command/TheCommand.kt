package me.liuli.cb.listen.command

import cn.nukkit.command.CommandSender
import me.liuli.cb.CheatBlocker

open class TheCommand(val name: String, val description: String) {
    open fun exec(commandSender: CommandSender, args: List<String>){

    }

    open fun syntax(commandSender: CommandSender, usage: String){

    }

    fun message(commandSender: CommandSender, msg: String){
        commandSender.sendMessage("${CheatBlocker.coloredName} §7> §f$msg")
    }
}
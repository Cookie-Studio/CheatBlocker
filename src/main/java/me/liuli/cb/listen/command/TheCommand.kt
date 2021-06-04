package me.liuli.cb.listen.command

import cn.nukkit.command.CommandSender
import me.liuli.cb.CheatBlocker

open class TheCommand(val name: String, val description: String, val syntaxStr: String = "") {
    open fun exec(commandSender: CommandSender, args: Array<String>) {

    }

    open fun syntax(commandSender: CommandSender, usage: String) {
        message(commandSender, "Usage: $usage")
    }

    fun message(commandSender: CommandSender, msg: String) {
        commandSender.sendMessage("${CheatBlocker.coloredName} §7> §f$msg")
    }

    fun defaultSyntax(commandSender: CommandSender) {
        syntax(commandSender, syntaxStr)
    }
}
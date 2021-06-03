package me.liuli.cb.listen.command.commands

import cn.nukkit.command.CommandSender
import me.liuli.cb.listen.command.TheCommand

class KickCommand : TheCommand("kick","kick players") {
    override fun exec(commandSender: CommandSender, args: List<String>) {
        commandSender.sendMessage("RECEIVED! ARGS: $args")
    }
}
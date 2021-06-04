package me.liuli.cb.listen.command.commands

import cn.nukkit.command.CommandSender
import me.liuli.cb.CheatBlocker
import me.liuli.cb.listen.command.TheCommand

class UnbanCommand : TheCommand("unban", "unban a banned player/ip") {
    override fun exec(commandSender: CommandSender, args: Array<String>) {
        if (args.isEmpty()) {
            defaultSyntax(commandSender)
            return
        }

        val punishManager = CheatBlocker.getInstance().punishManager

        val playerEnum = punishManager.unbanPlayer(args[0])
        if (playerEnum == 0) {
            if (punishManager.unbanIP(args[0])) {
                message(commandSender, "Successful unban IP!")
            } else {
                message(commandSender, "Player/IP not exists.")
            }
        } else if (playerEnum == 1) {
            message(commandSender, "Player not banned.")
        } else {
            message(commandSender, "Successful unban player!")
        }
    }
}
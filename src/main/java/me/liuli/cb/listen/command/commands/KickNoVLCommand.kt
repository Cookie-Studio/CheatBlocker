package me.liuli.cb.listen.command.commands

import cn.nukkit.command.CommandSender
import me.liuli.cb.CheatBlocker
import me.liuli.cb.listen.command.TheCommand

class KickNoVLCommand : TheCommand("kicknovl", "kick a player without vl increase", "<player> [<reason>]") {
    override fun exec(commandSender: CommandSender, args: Array<String>) {
        if (args.isEmpty()) {
            defaultSyntax(commandSender)
            return
        }
        val result: Boolean
        if (args.size > 1) {
            result = CheatBlocker.getInstance().punishManager.kickPlayerNoVL(
                args[0],
                reason = args[1],
                operator = commandSender.name
            )
        } else {
            result = CheatBlocker.getInstance().punishManager.kickPlayerNoVL(args[0], operator = commandSender.name)
        }
        if (result) {
            message(commandSender, "Player kicked.")
        } else {
            message(commandSender, "Player not exist.")
        }
    }
}
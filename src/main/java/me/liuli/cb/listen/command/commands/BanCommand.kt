package me.liuli.cb.listen.command.commands

import cn.nukkit.Server
import cn.nukkit.command.CommandSender
import me.liuli.cb.CheatBlocker
import me.liuli.cb.listen.command.TheCommand
import java.util.concurrent.TimeUnit

class BanCommand : TheCommand("ban", "ban a player", "<player> [<reason> <time> <time_unit>]") {
    override fun exec(commandSender: CommandSender, args: Array<String>) {
        if (args.isEmpty()) {
            defaultSyntax(commandSender)
            return
        }
        val punishConfig = CheatBlocker.getInstance().configManager.punishConfig
        val punishManager = CheatBlocker.getInstance().punishManager

        if (!punishManager.dataExists(args[0])) {
            message(commandSender, "Player not exists!")
            return
        }

        val data = punishManager.getOrCreateData(args[0])
        var reason = punishConfig.banDefaultReason
        var time = punishConfig.expirationTime
        var timeUnit = TimeUnit.valueOf(punishConfig.expirationTimeUnit.toUpperCase())
        if (args.size > 1) {
            reason = args[1]
            if (args.size > 2) {
                time = args[2].toLong()
                if (args.size > 3) {
                    timeUnit = TimeUnit.valueOf(args[3].toUpperCase())
                }
            }
        }

        val millsTime = TimeUnit.MILLISECONDS.convert(time, timeUnit)
        punishManager.banPlayer(data, reason, millsTime, commandSender.name)
        message(commandSender, "Player banned!")
        val ip = (data["ip"] ?: "") as String
        if (punishConfig.banWithIP && ip.isNotBlank()) {
            punishManager.banIP(ip, reason, millsTime, commandSender.name)
        }
        // kick the player if online
        val player = Server.getInstance().getPlayer(args[0])
        if (player != null) {
            punishManager.checkPlayer(player)
        }
    }
}
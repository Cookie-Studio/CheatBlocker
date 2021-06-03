package me.liuli.cb.listen.command

import cn.nukkit.command.Command
import cn.nukkit.command.CommandSender

class SubCommandListener(name: String, private val commandListener: CommandListener) : Command(name, commandListener.description) {
    override fun execute(p0: CommandSender, p1: String, p2: Array<out String>): Boolean {
        return commandListener.execute(p0, p1, p2)
    }
}
package me.liuli.cb.listen.command

import cn.nukkit.command.Command
import cn.nukkit.command.CommandSender
import me.liuli.cb.CheatBlocker
import me.liuli.cb.listen.command.commands.BanCommand
import me.liuli.cb.listen.command.commands.HelpCommand
import me.liuli.cb.listen.command.commands.KickCommand
import me.liuli.cb.listen.command.commands.UnbanCommand

class CommandListener(name: String, description: String) : Command(name, description) {
    private val cheatBlocker=CheatBlocker.getInstance()

    private val commands=HashMap<String,TheCommand>()

    init {
        registerCommand(HelpCommand(commands))
        registerCommand(KickCommand())
        registerCommand(BanCommand())
        registerCommand(UnbanCommand())
    }

    private fun registerCommand(command: TheCommand){
        commands[command.name]=command
    }

    override fun execute(p0: CommandSender, p1: String, p2: Array<String>): Boolean {
        if(p2.isEmpty()){
            p0.sendMessage(CheatBlocker.coloredName+" §fv"+cheatBlocker.description.version+" §fby §6"+ cheatBlocker.description.authors[0])
            if(p0.hasPermission(cheatBlocker.PERM_COMMAND)){
                p0.sendMessage("Use /$p1 help for further informations")
            }
            return false
        }

        if(!p0.hasPermission(cheatBlocker.PERM_COMMAND)){
            p0.sendMessage("${CheatBlocker.coloredName} §7> §fYou dont have permission to use this command.")
            return false
        }

        val command=commands[p2[0].toLowerCase()]
        if(command==null){
            p0.sendMessage("${CheatBlocker.coloredName} §7> §fUnknown Command.\nUse /$p1 help for further informations")
            return false
        }

        val array=p2.copyOfRange(1, p2.size)
        command.exec(p0,array)

        return false
    }
}
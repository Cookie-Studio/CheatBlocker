package me.liuli.cb.listen.command.commands

import cn.nukkit.command.CommandSender
import me.liuli.cb.CheatBlocker
import me.liuli.cb.listen.command.TheCommand

class HelpCommand(private val commands: HashMap<String,TheCommand>) : TheCommand("help","show the list of commands") {
    override fun exec(commandSender: CommandSender, args: Array<String>) {
        commandSender.sendMessage("Commands of ${CheatBlocker.coloredName} §f(${commands.size-1}):")

        commands.forEach{
            if(it.value != this){
                val comm=if(it.value.syntaxStr.isEmpty()){
                    "/${CheatBlocker.shortName} ${it.value.name}"
                }else{
                    "/${CheatBlocker.shortName} ${it.value.name} ${it.value.syntaxStr}"
                }
                commandSender.sendMessage("$comm - ${it.value.description}")
            }
        }
    }
}
package me.liuli.cb.listen.command.commands

import cn.nukkit.command.CommandSender
import me.liuli.cb.CheatBlocker
import me.liuli.cb.listen.command.TheCommand

class KickCommand : TheCommand("kick","kick players","<player> [<reason>]") {
    override fun exec(commandSender: CommandSender, args: Array<String>) {
        if(args.isEmpty()){
            defaultSyntax(commandSender)
            return
        }
        var result=false
        if(args.size>1){
            result=CheatBlocker.getInstance().punishManager.kickPlayer(args[0],reason = args[1],operator = commandSender.name)
        }else{
            result=CheatBlocker.getInstance().punishManager.kickPlayer(args[0],operator = commandSender.name)
        }
        if(result){
            message(commandSender,"Player kicked.")
        }else{
            message(commandSender,"Player not exist.")
        }
    }
}
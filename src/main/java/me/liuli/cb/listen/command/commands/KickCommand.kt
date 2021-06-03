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
        if(args.size>1){
            CheatBlocker.getInstance().punishManager.kickPlayer(args[0],reason = args[1],operator = commandSender.name)
        }else{
            CheatBlocker.getInstance().punishManager.kickPlayer(args[0],operator = commandSender.name)
        }
    }
}
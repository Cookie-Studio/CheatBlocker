package me.liuli.cb.listen.command.commands

import cn.nukkit.command.CommandSender
import me.liuli.cb.CheatBlocker
import me.liuli.cb.listen.command.TheCommand

class KickTimesCommand : TheCommand("kicktimes","show/change kicked times of a player","<player> [<times>]") {
    override fun exec(commandSender: CommandSender, args: Array<String>) {
        if(args.isEmpty()){
            defaultSyntax(commandSender)
            return
        }

        val data=CheatBlocker.getInstance().punishManager.getOrCreateData(args[0])

        if(args.size==1){
            message(commandSender,"${args[0]} kicked ${data["kicked_times"]} times")
        }else{
            data["kicked_times"]=args[1].toInt()
            data.save()
            message(commandSender,"${args[0]} kicked times to ${data["kicked_times"]}")
        }
    }
}
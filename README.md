# CheatBlocker
A free anticheat base for NukkitX(api version 1.0)  
*I just want to code a anticheat lol,but i dont want 2 play bedrock edition any more so i make this open-source*

# How can you make this to a anticheat
1. download it  
2. VL system is **NOT** done. Remember to code a dynamic increase vl system  
3. packet check should be async (think about AACv4/Old Matrix and ServerCrasher lol)  
4. register commands: 
~~~kotlin
import me.liuli.cb.listen.command.TheCommand

class ExampleCommand : TheCommand("example","a example command","usage"){
    override fun exec(commandSender: CommandSender, args: Array<String>) {
        
    }
}
~~~
and register it in CommandListener(me.liuli.cb.listen.command.CommandLister)  
5. add checks
> CHECK SYSTEM IS NOT DONE, YOU NEED TO ADD SOMETHING BY YOURSELF
~~~kotlin
class ExampleCheck : MoveCheck(CheckType.MOVE, "subcheck"/*config.yml section name*/, true/*use fly flag(flag like NCP),false will flag like AAC4(add this by self)*/) {
// override methods to check
}
~~~
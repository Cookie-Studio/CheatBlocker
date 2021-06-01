package me.liuli.atb;

import cn.nukkit.permission.Permission;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.plugin.PluginLogger;
import cn.nukkit.plugin.PluginManager;
import me.liuli.atb.listen.PacketListener;
import me.liuli.atb.listen.PlayerListener;
import me.liuli.atb.listen.UpdateTask;

public class AntiToolBox extends PluginBase {
    private static AntiToolBox instance;
    private static PluginLogger theLogger;

    @Override
    public void onEnable(){
        PluginManager pm=this.getServer().getPluginManager();
        theLogger=this.getLogger();

        if(!this.getDataFolder().exists() && !this.getDataFolder().mkdirs()){
            theLogger.warning("CANNOT PARPARE PLUGIN DATA FOLDER!");
            pm.disablePlugin(this);
            return;
        }

        // register perms
        pm.addPermission(new Permission("atb.check.bypass.packet","Bypass AntiToolBox Packet Checks",Permission.DEFAULT_OP));
        pm.addPermission(new Permission("atb.check.bypass.move","Bypass AntiToolBox Move Checks",Permission.DEFAULT_OP));
        pm.addPermission(new Permission("atb.command", "Use /atb command", Permission.DEFAULT_OP));

        // register listener
        this.getServer().getScheduler().scheduleRepeatingTask(new UpdateTask(), 1, false);
        pm.registerEvents(new PacketListener(), this);
        pm.registerEvents(new PlayerListener(), this);
    }

    public static AntiToolBox getInstance() {
        return instance;
    }

    public static PluginLogger getTheLogger() {
        return theLogger;
    }
}
package me.liuli.cb;

import cn.nukkit.permission.Permission;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.plugin.PluginLogger;
import cn.nukkit.plugin.PluginManager;
import me.liuli.cb.listen.PacketListener;
import me.liuli.cb.listen.PlayerListener;
import me.liuli.cb.listen.UpdateTask;
import me.liuli.cb.manage.CheckManager;
import me.liuli.cb.manage.ConfigManager;
import me.liuli.cb.manage.PlayerManager;

public class CheatBlocker extends PluginBase {
    public static String name = "CheatBlocker";
    public static String shortName = "cb";
    public static String coloredName = "§eCheat§cBlocker";

    private static CheatBlocker instance;
    private static PluginLogger theLogger;

    private CheckManager checkManager;
    private ConfigManager configManager;
    private PlayerManager playerManager;

    public static CheatBlocker getInstance() {
        return instance;
    }

    public static PluginLogger getTheLogger() {
        return theLogger;
    }

    @Override
    public void onEnable() {
        long loadTime = System.currentTimeMillis();
        instance = this;
        PluginManager pm = this.getServer().getPluginManager();
        theLogger = this.getLogger();

        if (!this.getDataFolder().exists() && !this.getDataFolder().mkdirs()) {
            theLogger.warning("CANNOT PREPARE PLUGIN DATA FOLDER!");
            pm.disablePlugin(this);
            return;
        }

        // init functions
        configManager = new ConfigManager();
        checkManager = new CheckManager();

        // register perms
        pm.addPermission(new Permission(shortName + ".check.bypass.packet", "Bypass " + name + " Packet Checks", Permission.DEFAULT_OP));
        pm.addPermission(new Permission(shortName + ".check.bypass.move", "Bypass " + name + " Move Checks", Permission.DEFAULT_OP));
        pm.addPermission(new Permission(shortName + ".command", "Use /" + shortName + " command", Permission.DEFAULT_OP));

        // register listener
        this.getServer().getScheduler().scheduleRepeatingTask(new UpdateTask(), 1, false);
        pm.registerEvents(new PacketListener(), this);
        pm.registerEvents(new PlayerListener(), this);

        theLogger.info(coloredName + " §fby Liuli enabled! (" + (System.currentTimeMillis() - loadTime) + " ms)");
    }

    public CheckManager getCheckManager() {
        return checkManager;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public PlayerManager getPlayerManager() {
        return playerManager;
    }
}
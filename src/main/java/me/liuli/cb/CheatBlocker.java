package me.liuli.cb;

import cn.nukkit.permission.Permission;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.plugin.PluginLogger;
import cn.nukkit.plugin.PluginManager;
import me.liuli.cb.listen.PacketListener;
import me.liuli.cb.listen.PlayerListener;
import me.liuli.cb.listen.UpdateTask;
import me.liuli.cb.listen.command.CommandListener;
import me.liuli.cb.listen.command.SubCommandListener;
import me.liuli.cb.manage.CheckManager;
import me.liuli.cb.manage.ConfigManager;
import me.liuli.cb.manage.PlayerManager;
import me.liuli.cb.manage.PunishManager;

public class CheatBlocker extends PluginBase {
    public static String name = "CheatBlocker";
    public static String shortName = "cb";
    public static String coloredName = "§eCheat§cBlocker";

    public Permission PERM_CHECK_BYPASS_PACKET=new Permission(shortName+".check.bypass.packet", "Bypass " + name + " Packet Checks", Permission.DEFAULT_OP);
    public Permission PERM_CHECK_BYPASS_MOVE=new Permission(shortName+".check.bypass.move", "Bypass " + name + " Move Checks", Permission.DEFAULT_OP);
    public Permission PERM_COMMAND=new Permission(shortName+".command", "Use /" + shortName + " command", Permission.DEFAULT_OP);

    private static CheatBlocker instance;
    private static PluginLogger theLogger;

    private CheckManager checkManager;
    private ConfigManager configManager;
    private PlayerManager playerManager;
    private PunishManager punishManager;

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
        playerManager = new PlayerManager();
        punishManager = new PunishManager();
        checkManager = new CheckManager();

        // register perms
        pm.addPermission(PERM_CHECK_BYPASS_PACKET);
        pm.addPermission(PERM_CHECK_BYPASS_MOVE);
        pm.addPermission(PERM_COMMAND);

        // register command
        CommandListener commandListener=new CommandListener(shortName,"Command of "+name);
        this.getServer().getCommandMap().register(shortName, commandListener);
        this.getServer().getCommandMap().register(name.toLowerCase(), new SubCommandListener(name.toLowerCase(),commandListener));

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

    public PunishManager getPunishManager() {
        return punishManager;
    }
}
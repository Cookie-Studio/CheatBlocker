package me.liuli.cb.data.config;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class PunishConfig extends TheConfig {
    @ConfigField(yamlSection = "expiration_time")
    public long expirationTime;

    @ConfigField(yamlSection = "expiration_time_unit")
    public String expirationTimeUnit;

    @ConfigField(yamlSection = "kick_default_reason")
    public String kickDefaultReason;

    @ConfigField(yamlSection = "ban_default_reason")
    public String banDefaultReason;

    @ConfigField(yamlSection = "ban_ip_chain_reason")
    public String banIPChainReason;

    @ConfigField(yamlSection = "ban_with_ip")
    public boolean banWithIP;

    @ConfigField(yamlSection = "ip_ban_chain")
    public boolean banIPChain;

    @ConfigField(yamlSection = "threshold")
    public int threshold;

    @ConfigField(yamlSection = "kick_commands")
    public List<String> kickCommands;

    @ConfigField(yamlSection = "ban_commands")
    public List<String> banCommands;

    @ConfigField(yamlSection = "kick_ui")
    public List<String> kickUI;

    @ConfigField(yamlSection = "ban_ui")
    public List<String> banUI;

    @ConfigField(yamlSection = "ban_ip_ui")
    public List<String> banIpUI;

    public long banTime;

    public PunishConfig() {
        super("punish.yml");
        if (expirationTime <= 0) {
            banTime = -1;
        } else {
            banTime = TimeUnit.MILLISECONDS.convert(expirationTime, TimeUnit.valueOf(expirationTimeUnit.toUpperCase()));
        }
    }
}

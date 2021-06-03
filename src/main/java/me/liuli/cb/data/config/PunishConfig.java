package me.liuli.cb.data.config;

import java.util.List;

public class PunishConfig extends TheConfig {
    @ConfigField(yamlSection = "expiration_time")
    public int expirationTime;

    @ConfigField(yamlSection = "expiration_time_unit")
    public String expirationTimeUnit;

    @ConfigField(yamlSection = "threshold")
    public int threshold;

    @ConfigField(yamlSection = "kick_commands")
    public List<String> kickCommands;

    @ConfigField(yamlSection = "ban_commands")
    public List<String> banCommands;

    public PunishConfig() {
        super("punish.yml");
    }
}

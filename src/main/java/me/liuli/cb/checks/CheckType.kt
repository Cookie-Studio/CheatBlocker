package me.liuli.cb.checks

import me.liuli.cb.CheatBlocker
import me.liuli.cb.data.check.MitigationData

enum class CheckType(val friendlyName: String, val yamlName: String) {
    MOVE("Move", "move"),
    KILLAURA("KillAura", "killaura");

    val enable: Boolean
    val mitigation: MitigationData

    init {
        val section = CheatBlocker.getInstance().configManager.checkConfig.config.getSection(yamlName)
        enable = section.getBoolean("enabled")
        mitigation = MitigationData(section.getSection("mitigation"))
    }
}
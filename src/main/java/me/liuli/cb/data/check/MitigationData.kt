package me.liuli.cb.data.check

import cn.nukkit.utils.ConfigSection

class MitigationData(val enable: Boolean, val minVl: Int) {
    constructor(mitigationSection: ConfigSection) : this(mitigationSection.getBoolean("enabled"),mitigationSection.getInt("min_vl"))

    override fun toString(): String {
        return "MitigationData(enabled=$enable, minVl=$minVl)"
    }
}
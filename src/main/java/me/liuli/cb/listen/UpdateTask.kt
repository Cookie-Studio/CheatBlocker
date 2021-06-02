package me.liuli.cb.listen

import cn.nukkit.scheduler.Task
import me.liuli.cb.CheatBlocker

class UpdateTask : Task() {
    private val checkManager= CheatBlocker.getInstance().checkManager

    override fun onRun(tick: Int) {
        checkManager.handleUpdate()
    }
}
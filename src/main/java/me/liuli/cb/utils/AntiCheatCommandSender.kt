package me.liuli.cb.utils

import cn.nukkit.command.ConsoleCommandSender

class AntiCheatCommandSender(val senderName: String="CheatBlocker") : ConsoleCommandSender() {
    override fun getName(): String {
        return senderName
    }
}
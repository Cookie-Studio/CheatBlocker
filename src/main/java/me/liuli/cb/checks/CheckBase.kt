package me.liuli.cb.checks

abstract class CheckBase(val type: CheckType, val subCheck: String) {
    // called every tick
    open fun onTick() {

    }

    // called when a player join game
    open fun onPlayerJoin() {

    }

    // called when a player leave game
    open fun onPlayerLeave() {

    }
}
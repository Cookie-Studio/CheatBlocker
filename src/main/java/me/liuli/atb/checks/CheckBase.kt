package me.liuli.atb.checks

abstract class CheckBase {
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
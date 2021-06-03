package me.liuli.cb.utils

import kotlin.math.floor

object StringUtils {
    fun t2s(time: Long): String {
        var timeCache = time
        val D = floor((timeCache / 86400).toDouble()).toInt()
        timeCache -= D * 86400L
        val H = floor((timeCache / 3600).toDouble()).toInt()
        timeCache -= H * 3600L
        val M = floor((timeCache / 60).toDouble()).toInt()
        timeCache -= M * 60L
        val result = StringBuilder()
        if (D != 0) {
            result.append(D)
            result.append("d ")
        }
        if (H != 0) {
            result.append(H)
            result.append("h ")
        }
        if (M != 0) {
            result.append(M)
            result.append("m ")
        }
        result.append(timeCache)
        result.append("s")
        return result.toString()
    }
}
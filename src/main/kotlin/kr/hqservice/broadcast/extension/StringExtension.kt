package kr.hqservice.broadcast.extension

import org.bukkit.ChatColor

internal fun String.isInt(): Boolean {
    return try {
        this.toInt()
        true
    } catch (e: NumberFormatException) {
        false
    }
}

internal fun String.applyColor(): String {
    return ChatColor.translateAlternateColorCodes('&', this)
}

internal fun MutableList<String>.applyColor(): MutableList<String> {
    return map { it.applyColor() }.toMutableList()
}
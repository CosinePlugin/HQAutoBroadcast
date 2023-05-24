package kr.hqservice.broadcast.extension

import org.bukkit.command.CommandSender

fun CommandSender.sendMessages(vararg message: String?) {
    message.filterNotNull().forEach { sendMessage(it) }
}
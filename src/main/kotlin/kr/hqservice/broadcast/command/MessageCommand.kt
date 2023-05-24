package kr.hqservice.broadcast.command

import kr.hqservice.broadcast.HQAutoBroadcast
import kr.hqservice.broadcast.HQAutoBroadcast.Companion.prefix
import kr.hqservice.broadcast.extension.sendMessages
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter

class MessageCommand(plugin: HQAutoBroadcast) : CommandExecutor, TabCompleter {

    private companion object {
        val commandTabList = mutableListOf("리로드")
    }

    private val messageRepository = plugin.messageRepository

    override fun onTabComplete(sender: CommandSender, command: Command, label: String, args: Array<String>): List<String>? {
        if (args.size <= 1) {
            return commandTabList
        }
        return emptyList()
    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {
        if (args.isEmpty()) {
            printHelp(sender)
            return true
        }
        checker(sender, args)
        return true
    }

    private fun printHelp(sender: CommandSender) {
        sender.sendMessages(
            "$prefix 공지 관리 명령어 도움말",
            "",
            "$prefix /공지관리 리로드"
        )
    }

    private fun checker(sender: CommandSender, args: Array<String>) {
        when (args[0]) {
            "리로드" -> reload(sender)

            else -> printHelp(sender)
        }
    }

    private fun reload(sender: CommandSender) {
        messageRepository.reload()
        sender.sendMessage("$prefix config.yml이 리로드되었습니다.")
    }
}
package kr.hqservice.broadcast

import kr.hqservice.broadcast.command.MessageCommand
import kr.hqservice.broadcast.repository.MessageRepository
import org.bukkit.plugin.java.JavaPlugin

class HQAutoBroadcast : JavaPlugin() {

    companion object {
        internal const val prefix = "§6[ §f공지 §6]§f"
    }

    lateinit var messageRepository: MessageRepository
        private set

    override fun onEnable() {
        messageRepository = MessageRepository(this)
        messageRepository.load()
        messageRepository.run()

        getCommand("공지관리")?.setExecutor(MessageCommand(this))
    }

    override fun onDisable() {
        messageRepository.stop()
    }
}
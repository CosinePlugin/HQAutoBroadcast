package kr.hqservice.broadcast.repository

import kr.hqservice.broadcast.HQAutoBroadcast
import kr.hqservice.broadcast.extension.applyColor
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.scheduler.BukkitTask
import java.io.File

class MessageRepository(
    private val plugin: HQAutoBroadcast
) {

    private companion object {
        const val path = "config.yml"
    }

    private val server = plugin.server

    private var file: File
    private var config: YamlConfiguration

    init {
        val newFile = File(plugin.dataFolder, path)
        if (!newFile.exists() && plugin.getResource(path) != null) {
            plugin.saveResource(path, false)
        }
        file = newFile
        config = YamlConfiguration.loadConfiguration(file)
    }

    private var delay: Long = 60

    private var isRandom = true

    private var messages = mutableListOf<String>()

    private var count = 0

    private var task: BukkitTask? = null

    fun load() {
        delay = config.getLong("delay") * 20

        isRandom = config.getBoolean("random")

        messages = config.getStringList("messages").applyColor()
    }

    fun reload() {
        stop()
        resetCount()
        config.load(file)
        messages.clear()
        load()
        run()
    }

    fun run() {
        task = server.scheduler.runTaskTimerAsynchronously(plugin, {
            val message = getMessage()

            server.broadcastMessage(message)

            if (addCount() >= getMessageSize()) {
                resetCount()
            }
        }, delay, delay)
    }

    fun stop() {
        task?.cancel()
        task = null
    }

    private fun getMessage(): String {
        return if (isRandom) messages.random() else messages[count]
    }

    private fun getMessageSize() = messages.size

    private fun addCount(): Int {
        count++
        return count
    }

    private fun resetCount() {
        count = 0
    }
}
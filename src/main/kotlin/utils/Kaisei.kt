package utils

import com.moandjiezana.toml.Toml
import com.moandjiezana.toml.TomlWriter
import java.io.File

class Kaisei {
    private val countdownFile = "countdown.toml"
    private val settingsFile = "settings.toml"

    data class CountdownData(@JvmField var eventName: String, @JvmField var date: String)

    fun countdownExist(): Boolean {
        return File(countdownFile).exists()
    }

    fun saveCountdown(data: CountdownData) {
        val tomlFile = File(countdownFile)
        val tomlWriter = TomlWriter()
        tomlWriter.write(data, tomlFile)
    }

    fun resetCountdown() {
        val file = File(countdownFile)
        if (file.exists()) {
            file.delete()
        }
    }

    fun getCountdown(): CountdownData? {
        return Toml().read(File(countdownFile)).to(CountdownData::class.java)
    }

    fun saveSetting(key: String, value: Boolean) {
        val toml = Toml().read(readFile(settingsFile) ?: "")
        val settings = HashMap(toml.toMap())
        settings[key] = value
        TomlWriter().write(settings, File(settingsFile))
    }

    fun getSetting(key: String, defaultValue: Boolean): Boolean {
        val toml = Toml().read(readFile(settingsFile) ?: "")
        return toml.getBoolean(key) ?: defaultValue
    }

    fun saveStringSetting(key: String, value: String) {
        val toml = Toml().read(readFile(settingsFile) ?: "")
        val settings = HashMap(toml.toMap())
        settings[key] = value
        TomlWriter().write(settings, File(settingsFile))
    }

    fun getStringSetting(key: String, defaultValue: String): String {
        val toml = Toml().read(readFile(settingsFile) ?: "")
        return toml.getString(key) ?: defaultValue
    }

    private fun readFile(path: String): String? {
        return try {
            File(path).readText()
        } catch (e: Exception) {
            null
        }
    }
}
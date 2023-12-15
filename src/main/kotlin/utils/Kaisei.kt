package utils

import com.moandjiezana.toml.Toml
import com.moandjiezana.toml.TomlWriter
import java.io.File

class Kaisei {
    private val countdownFile = "countdown.toml"
    private val settingsFile = "settings.toml"
    private var toml = Toml().read(File(settingsFile))
    private var settings = HashMap(toml.toMap())

    data class CountdownData(@JvmField var eventName: String = "Event", @JvmField var date: String = "2023-12-31")

    fun saveCountdown(file: File, data: CountdownData) {
        val tomlFile = File(countdownFile)
        val tomlWriter = TomlWriter()
        tomlWriter.write(data, tomlFile)
    }

    fun saveSetting(key: String, value: Boolean) {
        settings[key] = value
        TomlWriter().write(settings, File(settingsFile))
    }

    fun getSetting(key: String, defaultValue: Boolean): Boolean {
        return toml.getBoolean(key) ?: defaultValue
    }

    fun saveStringSetting(key: String, value: String) {
        settings[key] = value
        TomlWriter().write(settings, File(settingsFile))
    }

    fun getStringSetting(key: String, defaultValue: String): String {
        return toml.getString(key) ?: defaultValue
    }
}
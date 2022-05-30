package com.example.domain.repo

interface SettingsRepository {
    companion object {
        const val NIGHT_SETTINGS_KEY = "enableDarkMode"
        const val API_SETTINGS_KEY = "useApi"
        const val DB_SETTINGS_KEY = "useDb"
    }

    fun setOption(name: String, value: Boolean)
    fun getOption(name: String): Boolean
}
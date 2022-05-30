package com.example.infrastructure.storage

import android.content.SharedPreferences
import javax.inject.Inject

class StorageImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences,
): Storage {
    override fun save(key: String, value: Boolean) =
        sharedPreferences.edit().putBoolean(key, value).apply()

    override fun get(key: String): Boolean = sharedPreferences.getBoolean(key, false)
}
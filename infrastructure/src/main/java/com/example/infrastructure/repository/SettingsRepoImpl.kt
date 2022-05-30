package com.example.infrastructure.repository

import com.example.domain.repo.SettingsRepository
import com.example.infrastructure.storage.Storage
import javax.inject.Inject

class SettingsRepoImpl @Inject constructor(
    private val storage: Storage,
): SettingsRepository {
    override fun setOption(name: String, value: Boolean) = storage.save(name, value)
    override fun getOption(name: String): Boolean = storage.get(name)
}
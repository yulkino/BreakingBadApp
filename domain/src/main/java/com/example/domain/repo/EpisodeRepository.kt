package com.example.domain.repo

import com.example.domain.models.Episode

interface EpisodeRepository {
    suspend fun getAll(fromApi: Boolean): List<Episode>

    suspend fun saveAll(list: List<Episode>)
}
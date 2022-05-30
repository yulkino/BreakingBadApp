package com.example.infrastructure.repository

import com.example.domain.models.Episode
import com.example.domain.repo.EpisodeRepository
import com.example.infrastructure.api.RetrofitServices
import com.example.infrastructure.db.dao.EpisodeDao
import com.example.infrastructure.db.entity.EpisodeEntity
import javax.inject.Inject

class EpisodeRepoImpl @Inject constructor(
    private val retrofitServices: RetrofitServices,
    private val dao: EpisodeDao,
): EpisodeRepository {
    override suspend fun getAll(fromApi: Boolean): List<Episode> =
        if (fromApi) retrofitServices.getAllEpisodes()
        else dao.getAll().map { it.toDomain() }

    override suspend fun saveAll(list: List<Episode>) {
        dao.saveAll(*list.map { EpisodeEntity.fromDomain(it) }.toTypedArray())
    }
}
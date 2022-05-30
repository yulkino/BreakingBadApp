package com.example.domain.usecases.impl

import com.example.domain.models.Episode
import com.example.domain.repo.EpisodeRepository
import com.example.domain.usecases.GetAllEpisodesUsecase
import com.example.domain.usecases.SaveEpisodesToDbUsecase
import javax.inject.Inject

class GetAllEpisodesFromApi @Inject constructor(
    private val repository: EpisodeRepository,
): GetAllEpisodesUsecase.FromApi() {
    override suspend fun invoke(): List<Episode> = repository.getAll(true)
}

class GetAllEpisodesFromDb @Inject constructor(
    private val repository: EpisodeRepository,
): GetAllEpisodesUsecase.FromDb() {
    override suspend fun invoke(): List<Episode> = repository.getAll(false)
}

class SaveAllEpisodes @Inject constructor(
    private val repository: EpisodeRepository,
): SaveEpisodesToDbUsecase {
    override suspend fun invoke(episodes: List<Episode>) = repository.saveAll(episodes)
}
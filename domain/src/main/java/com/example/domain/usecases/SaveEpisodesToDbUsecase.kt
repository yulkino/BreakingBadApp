package com.example.domain.usecases

import com.example.domain.models.Episode

interface SaveEpisodesToDbUsecase {
    suspend operator fun invoke(episodes: List<Episode>)
}
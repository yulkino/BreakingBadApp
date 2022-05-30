package com.example.domain.usecases

import com.example.domain.models.Episode

sealed class GetAllEpisodesUsecase {
    abstract suspend operator fun invoke(): List<Episode>

    abstract class FromApi: GetAllEpisodesUsecase()
    abstract class FromDb: GetAllEpisodesUsecase()
}
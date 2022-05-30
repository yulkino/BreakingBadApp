package com.example.domain.usecases

import com.example.domain.models.Person

sealed class GetAllCharsUsecase {
    abstract suspend operator fun invoke(): List<Person>

    abstract class FromApi: GetAllCharsUsecase()
    abstract class FromDb: GetAllCharsUsecase()
}
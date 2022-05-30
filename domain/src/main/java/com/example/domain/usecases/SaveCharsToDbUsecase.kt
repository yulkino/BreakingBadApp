package com.example.domain.usecases

import com.example.domain.models.Person

interface SaveCharsToDbUsecase {
    suspend operator fun invoke(chars: List<Person>)
}
package com.example.domain.usecases.impl

import com.example.domain.models.Person
import com.example.domain.repo.PersonRepository
import com.example.domain.usecases.GetAllCharsUsecase
import com.example.domain.usecases.SaveCharsToDbUsecase
import javax.inject.Inject

class GetAllCharsFromApi @Inject constructor(
    private val repository: PersonRepository,
): GetAllCharsUsecase.FromApi() {
    override suspend fun invoke(): List<Person> = repository.getAll(true)
}

class GetAllCharsFromDb @Inject constructor(
    private val repository: PersonRepository,
): GetAllCharsUsecase.FromDb() {
    override suspend fun invoke(): List<Person> = repository.getAll(false)
}

class SaveAllChars @Inject constructor(
    private val repository: PersonRepository,
): SaveCharsToDbUsecase {
    override suspend fun invoke(list: List<Person>) = repository.saveAll(list)
}
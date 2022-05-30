package com.example.infrastructure.repository

import com.example.domain.models.Person
import com.example.domain.repo.PersonRepository
import com.example.infrastructure.api.RetrofitServices
import com.example.infrastructure.db.dao.PersonDao
import com.example.infrastructure.db.entity.PersonEntity
import javax.inject.Inject

class PersonRepoImpl @Inject constructor(
    private val retrofitServices: RetrofitServices,
    private val dao: PersonDao,
): PersonRepository {
    override suspend fun getAll(fromApi: Boolean): List<Person> =
        if (fromApi) retrofitServices.getAllCharacters()
        else dao.getAll().map { it.toDomain() }

    override suspend fun saveAll(list: List<Person>) {
        dao.saveAll(*list.map { PersonEntity.fromDomain(it) }.toTypedArray())
    }
}
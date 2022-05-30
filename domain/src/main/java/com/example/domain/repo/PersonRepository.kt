package com.example.domain.repo

import com.example.domain.models.Person

interface PersonRepository {
    suspend fun getAll(fromApi: Boolean): List<Person>

    suspend fun saveAll(list: List<Person>)
}
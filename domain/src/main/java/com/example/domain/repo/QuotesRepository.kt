package com.example.domain.repo

import com.example.domain.models.Quote

interface QuotesRepository {
    suspend fun getAll(fromApi: Boolean): List<Quote>

    suspend fun saveAll(list: List<Quote>)
}
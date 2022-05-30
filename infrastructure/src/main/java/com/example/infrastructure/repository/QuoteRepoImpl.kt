package com.example.infrastructure.repository

import com.example.domain.models.Quote
import com.example.domain.repo.QuotesRepository
import com.example.infrastructure.api.RetrofitServices
import com.example.infrastructure.db.dao.QuoteDao
import com.example.infrastructure.db.entity.QuoteEntity
import javax.inject.Inject

class QuoteRepoImpl @Inject constructor(
    private val retrofitServices: RetrofitServices,
    private val dao: QuoteDao,
): QuotesRepository {
    override suspend fun getAll(fromApi: Boolean): List<Quote> =
        if (fromApi) retrofitServices.getAllQuotes()
        else dao.getAll().map { it.toDomain() }

    override suspend fun saveAll(list: List<Quote>) {
        dao.saveAll(*list.map { QuoteEntity.fromDomain(it) }.toTypedArray())
    }
}
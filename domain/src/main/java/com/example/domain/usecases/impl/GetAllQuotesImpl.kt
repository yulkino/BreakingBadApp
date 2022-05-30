package com.example.domain.usecases.impl

import com.example.domain.models.Quote
import com.example.domain.repo.QuotesRepository
import com.example.domain.usecases.GetAllQuotesUsecase
import com.example.domain.usecases.SaveQuotesToDbUsecase
import javax.inject.Inject

class GetAllQuotesFromApi @Inject constructor(
    private val repository: QuotesRepository,
): GetAllQuotesUsecase.FromApi() {
    override suspend fun invoke(): List<Quote> = repository.getAll(true)
}

class GetAllQuotesFromDb @Inject constructor(
    private val repository: QuotesRepository,
): GetAllQuotesUsecase.FromDb() {
    override suspend fun invoke(): List<Quote> = repository.getAll(false)
}

class SaveAllQuotes @Inject constructor(
    private val repository: QuotesRepository,
): SaveQuotesToDbUsecase {
    override suspend fun invoke(list: List<Quote>) = repository.saveAll(list)
}
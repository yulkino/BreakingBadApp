package com.example.domain.usecases

import com.example.domain.models.Quote

sealed class GetAllQuotesUsecase {
    abstract suspend operator fun invoke(): List<Quote>

    abstract class FromApi: GetAllQuotesUsecase()
    abstract class FromDb: GetAllQuotesUsecase()
}
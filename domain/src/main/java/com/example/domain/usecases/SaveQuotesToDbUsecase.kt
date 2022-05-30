package com.example.domain.usecases

import com.example.domain.models.Quote

interface SaveQuotesToDbUsecase {
    suspend operator fun invoke(list: List<Quote>)
}
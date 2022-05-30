package com.example.infrastructure.api

import com.example.domain.models.Episode
import com.example.domain.models.Person
import com.example.domain.models.Quote
import retrofit2.http.GET

interface RetrofitServices {
    @GET("characters")
    suspend fun getAllCharacters(): List<Person>
    @GET("episodes")
    suspend fun getAllEpisodes(): List<Episode>
    @GET("quotes")
    suspend fun getAllQuotes(): List<Quote>
}
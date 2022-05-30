package com.example.domain.models

import java.io.Serializable

data class Episode(
    val title: String,
    val season: String,
    val episode: String,
    val air_date: String,
    val characters: List<String>,
): Serializable
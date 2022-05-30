package com.example.domain.models

import java.io.Serializable

data class Quote(
    val quote: String,
    val author: String,
): Serializable
package com.example.domain.models

import java.io.Serializable

data class Person(
    val name: String,
    val birthday: String,
    val occupation: List<String>,
    val img: String,
    val status: String,
    val appearance: List<Int>,
): Serializable
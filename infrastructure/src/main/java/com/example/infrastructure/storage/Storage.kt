package com.example.infrastructure.storage

interface Storage {
    fun save(key: String, value: Boolean)
    fun get(key: String): Boolean
}
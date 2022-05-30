package com.example.infrastructure.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.domain.models.Quote

@Entity
data class QuoteEntity(
    val quote: String,
    val author: String,
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
) {
    companion object {
        fun fromDomain(quote: Quote) = QuoteEntity(quote.quote, quote.author)
    }

    fun toDomain() = Quote(quote, author)
}

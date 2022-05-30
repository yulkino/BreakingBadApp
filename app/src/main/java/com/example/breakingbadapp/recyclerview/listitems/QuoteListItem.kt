package com.example.breakingbadapp.recyclerview.listitems

import com.example.domain.models.Quote

data class QuoteListItem(
    val id: Int,
    val text: String,
    val author: String,
    val clicked: () -> Unit,
) {
    companion object {
        fun fromDomain(id: Int, quote: Quote, clicked: (Int) -> Unit) =
            QuoteListItem(
                id,
                quote.quote,
                quote.author,
            ) { clicked(id) }
    }
}
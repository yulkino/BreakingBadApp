package com.example.breakingbadapp.recyclerview.viewholders

import androidx.recyclerview.widget.RecyclerView
import com.example.breakingbadapp.databinding.ItemQuoteBinding
import com.example.breakingbadapp.recyclerview.listitems.QuoteListItem

class QuoteViewHolder(
    private val binding: ItemQuoteBinding,
): RecyclerView.ViewHolder(binding.root) {
    fun bind(quote: QuoteListItem) {
        with(binding) {
            text.text = "\"${quote.text}\""
            author.text = "- ${quote.author} (c)"
            root.setOnClickListener { quote.clicked() }
        }
    }
}
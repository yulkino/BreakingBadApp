package com.example.breakingbadapp.recyclerview.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.breakingbadapp.databinding.ItemQuoteBinding
import com.example.breakingbadapp.recyclerview.ItemCallback
import com.example.breakingbadapp.recyclerview.listitems.QuoteListItem
import com.example.breakingbadapp.recyclerview.viewholders.QuoteViewHolder

class QuoteAdapter: ListAdapter<QuoteListItem, QuoteViewHolder>(ItemCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuoteViewHolder =
        QuoteViewHolder(ItemQuoteBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        ))

    override fun onBindViewHolder(holder: QuoteViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
package com.example.breakingbadapp.recyclerview.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.breakingbadapp.databinding.ItemEpisodeBinding
import com.example.breakingbadapp.recyclerview.ItemCallback
import com.example.breakingbadapp.recyclerview.listitems.EpisodeListItem
import com.example.breakingbadapp.recyclerview.viewholders.EpisodeViewHolder

class EpisodeAdapter: ListAdapter<EpisodeListItem, EpisodeViewHolder>(ItemCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeViewHolder =
        EpisodeViewHolder(ItemEpisodeBinding.inflate(
            LayoutInflater.from(parent.context), parent, false),
        )

    override fun onBindViewHolder(holder: EpisodeViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
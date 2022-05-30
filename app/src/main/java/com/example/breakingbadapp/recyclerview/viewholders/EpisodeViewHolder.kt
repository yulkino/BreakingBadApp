package com.example.breakingbadapp.recyclerview.viewholders

import androidx.recyclerview.widget.RecyclerView
import com.example.breakingbadapp.databinding.ItemEpisodeBinding
import com.example.breakingbadapp.recyclerview.listitems.EpisodeListItem

class EpisodeViewHolder(
    private val binding: ItemEpisodeBinding,
): RecyclerView.ViewHolder(binding.root) {
    fun bind(episode: EpisodeListItem) {
        with(binding) {
            number.text = episode.number
            title.text = "\"${episode.title}\""
            root.setOnClickListener { episode.clicked() }
        }
    }
}
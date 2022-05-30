package com.example.breakingbadapp.recyclerview.listitems

import com.example.domain.models.Episode

data class EpisodeListItem(
    val id: Int,
    val number: String,
    val title: String,
    private val domain: Episode,
    val clicked: () -> Unit,
) {
    companion object {
        fun fromDomain(id: Int, episode: Episode, clicked: (Int) -> Unit) =
            EpisodeListItem(
                id,
                "Season ${episode.season} episode ${episode.episode}",
                episode.title,
                episode,
            ) { clicked(id) }
    }

    fun toDomain() = domain
}
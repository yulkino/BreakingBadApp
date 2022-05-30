package com.example.infrastructure.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.domain.models.Episode

@Entity
data class EpisodeEntity(
    val title: String,
    val season: String,
    val number: String,
    val date: String,
    val characters: String,
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
) {
    companion object {
        fun fromDomain(episode: Episode) =
            EpisodeEntity(
                episode.title,
                episode.season,
                episode.episode,
                episode.air_date,
                episode.characters.joinToString(", "),
            )
    }

    fun toDomain() = Episode(title, season, number, date, characters.split(", "))
}
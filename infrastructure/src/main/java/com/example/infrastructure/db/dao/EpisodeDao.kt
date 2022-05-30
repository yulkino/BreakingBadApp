package com.example.infrastructure.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.infrastructure.db.entity.EpisodeEntity

@Dao
interface EpisodeDao {
    @Query("select * from episodeentity")
    suspend fun getAll(): List<EpisodeEntity>
    @Insert
    suspend fun saveAll(vararg personEntity: EpisodeEntity)
}
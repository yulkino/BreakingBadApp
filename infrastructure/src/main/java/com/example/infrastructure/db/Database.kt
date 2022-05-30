package com.example.infrastructure.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.infrastructure.db.dao.EpisodeDao
import com.example.infrastructure.db.dao.PersonDao
import com.example.infrastructure.db.dao.QuoteDao
import com.example.infrastructure.db.entity.EpisodeEntity
import com.example.infrastructure.db.entity.PersonEntity
import com.example.infrastructure.db.entity.QuoteEntity

@Database(entities = [
    QuoteEntity::class,
    EpisodeEntity::class,
    PersonEntity::class,
], version = 4)
abstract class Database: RoomDatabase() {
    abstract fun episodeDao(): EpisodeDao
    abstract fun personDao(): PersonDao
    abstract fun quoteDao(): QuoteDao
}
package com.example.infrastructure.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.infrastructure.db.entity.QuoteEntity

@Dao
interface QuoteDao {
    @Query("select * from quoteentity")
    suspend fun getAll(): List<QuoteEntity>
    @Insert
    suspend fun saveAll(vararg personEntity: QuoteEntity)
}
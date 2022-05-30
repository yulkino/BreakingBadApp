package com.example.infrastructure.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.infrastructure.db.entity.PersonEntity

@Dao
interface PersonDao {
    @Query("select * from personentity")
    suspend fun getAll(): List<PersonEntity>
    @Insert
    suspend fun saveAll(vararg personEntity: PersonEntity)
}
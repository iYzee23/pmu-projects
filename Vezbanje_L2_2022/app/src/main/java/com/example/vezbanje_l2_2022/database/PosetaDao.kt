package com.example.vezbanje_l2_2022.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PosetaDao {
    @Query("SELECT * FROM poseta ORDER BY id DESC")
    fun getPosete(): Flow<List<Poseta>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(poseta: Poseta)

    @Query("DELETE FROM poseta")
    suspend fun deleteAll()
}
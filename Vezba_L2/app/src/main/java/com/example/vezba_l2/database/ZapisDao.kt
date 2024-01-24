package com.example.vezba_l2.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ZapisDao {
    @Query("SELECT * FROM zapisi ORDER BY redni DESC")
    fun getZapisi(): Flow<List<Zapis>>

    @Query("SELECT max(redni) FROM zapisi")
    fun getMaxRedni(): Flow<Int?>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(zapis: Zapis)

    @Query("DELETE FROM zapisi")
    suspend fun deleteAll()
}
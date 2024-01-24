package com.example.klijent.baza

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ObavezaDao {
    @Query("SELECT * FROM obaveza_table")
    fun getObaveze(): Flow<List<Obaveza>>

    @Query("SELECT * FROM obaveza_table ORDER BY vreme DESC")
    fun getObavezeAsc(): Flow<List<Obaveza>>

    @Query("SELECT * FROM obaveza_table WHERE fleg = 0")
    fun getNeodradjeneObaveze(): Flow<List<Obaveza>>

    @Query("SELECT * FROM obaveza_table WHERE fleg = 0 ORDER BY vreme DESC")
    fun getNeodradjeneObavezeAsc(): Flow<List<Obaveza>>

    @Update
    suspend fun updateObaveza(obaveza: Obaveza)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertObaveza(obaveza: Obaveza)

    @Query("DELETE FROM obaveza_table")
    suspend fun deleteAllObaveze()
}
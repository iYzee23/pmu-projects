package com.example.vezbanje_k2_2022.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.flow.Flow

@Dao
interface PrimeDao {
    @Query("SELECT * FROM primes")
    fun getRows(): Flow<List<Prime>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(prime: Prime)

    @Query("SELECT * FROM primes WHERE leader = :leader")
    suspend fun exists(leader: Int): Prime?
}
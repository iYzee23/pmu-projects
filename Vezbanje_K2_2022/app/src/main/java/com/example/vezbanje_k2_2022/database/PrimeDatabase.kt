package com.example.vezbanje_k2_2022.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.CoroutineScope

@Database(entities = [Prime::class], version = 1, exportSchema = false)
abstract class PrimeDatabase : RoomDatabase() {
    abstract fun primeDao(): PrimeDao

    companion object {
        var INSTANCE: PrimeDatabase? = null

        fun getDatabase(context: Context, coroutineScope: CoroutineScope): PrimeDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PrimeDatabase::class.java,
                    "prime_database"
                ).build()
                INSTANCE = instance
                return@synchronized instance
            }
        }
    }
}
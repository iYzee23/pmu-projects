package com.example.vezba_l2.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.CoroutineScope

@Database(entities = [Zapis::class], version = 1, exportSchema = false)
abstract class ZapisDatabase : RoomDatabase() {
    abstract fun zapisDao(): ZapisDao

    companion object {
        var INSTANCE:  ZapisDatabase? = null

        fun getDatabase(context: Context, coroutineScope: CoroutineScope): ZapisDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context = context.applicationContext,
                    klass = ZapisDatabase::class.java,
                    name = "zapisi_database"
                ).build()
                INSTANCE = instance
                return@synchronized instance
            }
        }
    }
}
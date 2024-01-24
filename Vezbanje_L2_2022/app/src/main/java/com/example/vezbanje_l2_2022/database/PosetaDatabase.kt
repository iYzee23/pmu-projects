package com.example.vezbanje_l2_2022.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [Poseta::class], version = 1, exportSchema = false)
abstract class PosetaDatabase: RoomDatabase() {
    abstract fun posetaDao(): PosetaDao

    companion object {
        private var INSTANCE: PosetaDatabase? = null
        fun getDatabase(context: Context, scope: CoroutineScope): PosetaDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context = context.applicationContext,
                    klass = PosetaDatabase::class.java,
                    name = "posetaDatabase"
                ).build()
                INSTANCE = instance
                return@synchronized instance
            }
        }
    }
}
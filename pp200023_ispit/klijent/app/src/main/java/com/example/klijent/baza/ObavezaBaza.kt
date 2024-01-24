package com.example.klijent.baza

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import kotlinx.coroutines.CoroutineScope

@TypeConverters(DateConverter::class)
@Database(entities = arrayOf(Obaveza::class), version = 1, exportSchema = false)
abstract class ObavezaBaza : RoomDatabase() {
    abstract fun obavezaDao(): ObavezaDao

    companion object {
        private var INSTANCE: ObavezaBaza? = null

        fun getDatabase(context: Context, scope: CoroutineScope): ObavezaBaza {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ObavezaBaza::class.java,
                    "obaveza_database"
                ).build()
                INSTANCE = instance
                // return instance
                return@synchronized instance
            }
        }
    }
}
package com.example.vezbanje_l2_2022.hilt

import android.content.Context
import com.example.vezbanje_l2_2022.database.PosetaDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides @Singleton
    fun providesDatabase(coroutineScope: CoroutineScope, @ApplicationContext context: Context) =
        PosetaDatabase.getDatabase(context, coroutineScope)

    @Provides @Singleton
    fun providesPosetaDao(database: PosetaDatabase) =
        database.posetaDao()
}
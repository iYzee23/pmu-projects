package com.example.vezba_l2.hilt

import android.content.Context
import com.example.vezba_l2.database.ZapisDatabase
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
    fun providesZapisDatabase(@ApplicationContext context: Context, coroutineScope: CoroutineScope) =
        ZapisDatabase.getDatabase(context, coroutineScope)

    @Provides @Singleton
    fun providesZapisDao(database: ZapisDatabase) =
        database.zapisDao()
}
package com.example.vezbanje_k2_2022.hilt

import android.content.Context
import com.example.vezbanje_k2_2022.database.PrimeDatabase
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
        PrimeDatabase.getDatabase(context, coroutineScope)

    @Provides @Singleton
    fun providesPrimeDao(database: PrimeDatabase) =
        database.primeDao()
}
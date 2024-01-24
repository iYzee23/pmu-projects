package com.example.vezbanje_l2_2022.hilt

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CoroutineScopeModel {
    @Provides @Singleton
    fun providesCoroutineScope() =
        CoroutineScope(SupervisorJob() + Dispatchers.Default)
}
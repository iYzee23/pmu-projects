package com.example.vezbanje_k2_2022.database

import kotlinx.coroutines.Deferred
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PrimeRepo @Inject constructor(private val primeDao: PrimeDao) {
    val rows = primeDao.getRows()

    suspend fun insert(prime: Prime) {
        primeDao.insert(prime)
    }

    suspend fun exists(leader: Int): Prime? {
        return primeDao.exists(leader)
    }
}
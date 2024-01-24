package com.example.vezba_l2.database

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ZapisRepo @Inject constructor(private val zapisDao: ZapisDao) {
    val zapisi = zapisDao.getZapisi()

    val maxRedni = zapisDao.getMaxRedni()

    suspend fun insert(zapis: Zapis) {
        zapisDao.insert(zapis)
    }

    suspend fun deleteAll() {
        zapisDao.deleteAll()
    }
}
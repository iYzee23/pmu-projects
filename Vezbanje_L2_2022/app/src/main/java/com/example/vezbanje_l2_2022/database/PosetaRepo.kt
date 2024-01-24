package com.example.vezbanje_l2_2022.database

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PosetaRepo @Inject constructor(private val posetaDao: PosetaDao) {
    val posete = posetaDao.getPosete()

    suspend fun insert(poseta: Poseta) { posetaDao.insert(poseta) }

    suspend fun deleteAll() { posetaDao.deleteAll() }
}
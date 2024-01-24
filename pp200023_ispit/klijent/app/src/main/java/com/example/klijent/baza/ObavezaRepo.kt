package com.example.klijent.baza

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ObavezaRepo @Inject constructor(private val obavezaDao: ObavezaDao) {
    val obaveze = obavezaDao.getObaveze()
    val obavezeAsc = obavezaDao.getObavezeAsc()
    val neodradjeneObaveze = obavezaDao.getNeodradjeneObaveze()
    val neodradjeneObavezeAsc = obavezaDao.getNeodradjeneObavezeAsc()

    suspend fun updateObaveza(obaveza: Obaveza) {
        obavezaDao.updateObaveza(obaveza)
    }

    suspend fun insertObaveza(obaveza: Obaveza) {
        obavezaDao.insertObaveza(obaveza)
    }

    suspend fun deleteAllObaveze() {
        obavezaDao.deleteAllObaveze()
    }
}
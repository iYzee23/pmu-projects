package com.example.klijent.retrofit

import javax.inject.Inject

class ObavezaRetroRepo @Inject constructor(private val obavezaRetroApi: ObavezaRetroApi) {
    suspend fun getObaveze(id: Int?) = obavezaRetroApi.getObaveze(id)
    suspend fun getNeodradjeneObaveze(id: Int?) = obavezaRetroApi.getNeodradjeneObaveze(id)
    suspend fun azurirajObavezu(id: Int) = obavezaRetroApi.azurirajFleg(id)
    suspend fun dodajObavezu(obaveza: ObavezaRetro) = obavezaRetroApi.dodajObavezu(obaveza)
}
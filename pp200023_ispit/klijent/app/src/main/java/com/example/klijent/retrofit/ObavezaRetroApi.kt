package com.example.klijent.retrofit

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

const val BASE_URL = "http://192.168.0.23:8080/"

interface ObavezaRetroApi {
    @GET("all")
    suspend fun getObaveze(@Query("id") id: Int?): List<ObavezaRetro>

    @GET("all/neodradjene")
    suspend fun getNeodradjeneObaveze(@Query("id") id: Int?): List<ObavezaRetro>

    @POST("all/azurirajFleg")
    suspend fun azurirajFleg(@Body id: Int)

    @POST("all/dodajObavezu")
    suspend fun dodajObavezu(@Body obaveza: ObavezaRetro)
}
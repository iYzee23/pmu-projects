package com.example.vezbanje_l3_2022.retrofit

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class JokesRepo @Inject constructor(private val jokesApi: JokesApi) {
    suspend fun getOneJoke(url: String) = jokesApi.getOneJoke(url)
    suspend fun getMoreJokes(url: String) = jokesApi.getMoreJokes(url)
}
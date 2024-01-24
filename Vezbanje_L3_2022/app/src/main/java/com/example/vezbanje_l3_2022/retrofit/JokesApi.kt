package com.example.vezbanje_l3_2022.retrofit

import retrofit2.http.GET
import retrofit2.http.Url

const val BASE_URL = "https://v2.jokeapi.dev/joke/"

interface JokesApi {
    @GET
    suspend fun getOneJoke(@Url url: String): Joke

    @GET
    suspend fun getMoreJokes(@Url url: String): JokeResponse
}
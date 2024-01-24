package com.example.vezbanje_l3_2022.retrofit

data class JokeResponse(
    val error: Boolean = false,
    val amount: Int = 0,
    val jokes: List<Joke> = listOf()
)
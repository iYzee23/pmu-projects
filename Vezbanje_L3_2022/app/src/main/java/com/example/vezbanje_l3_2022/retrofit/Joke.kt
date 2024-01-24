package com.example.vezbanje_l3_2022.retrofit

data class Joke(
    val error: Boolean = false,
    val category: String = "",
    val type: String = "",
    val setup: String? = null,
    val delivery: String? = null,
    val joke: String? = null,
    val flags: JokeFlags = JokeFlags(),
    val id: Int = 0,
    val safe: Boolean = false,
    val lang: String = ""
)
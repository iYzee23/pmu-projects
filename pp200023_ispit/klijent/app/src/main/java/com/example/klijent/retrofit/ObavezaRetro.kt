package com.example.klijent.retrofit

import java.util.Date

data class ObavezaRetro (
    val id: Int = 0,
    val fleg: Boolean = false,
    val naziv: String = "",
    val vreme: Date = Date(),
    val opis: String = ""
)
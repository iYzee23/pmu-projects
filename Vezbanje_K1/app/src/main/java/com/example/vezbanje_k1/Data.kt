package com.example.vezbanje_k1

import androidx.annotation.DrawableRes

data class Data (
    @DrawableRes val image: Int,
    val title: String,
    val text: String,
    var isFavourite: Boolean = false
)
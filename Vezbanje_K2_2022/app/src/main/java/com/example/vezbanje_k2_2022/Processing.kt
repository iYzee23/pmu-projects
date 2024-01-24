package com.example.vezbanje_k2_2022

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.delay

class Processing () {
    var leader by mutableStateOf(0)
    var first by mutableStateOf(0)
    var second by mutableStateOf(0)
    var third by mutableStateOf(0)
}

private fun isPrime(number: Int): Boolean {
    val upper = number - 1
    for (i in 2..upper) {
        if (number % i == 0) return false
    }
    return true
}

val Processing.progress: Float
    get() =
        if (first == 0) 0.00F
        else if (second == 0) 33.33F
        else if (third == 0) 66.66F
        else 100.00F

suspend fun Processing.run() {
    var i = leader + 1
    while (true) {
        if (isPrime(i)) {
            delay(500L)
            if (first == 0) first = i
            else if (second == 0) second = i
            else {
                third = i
                break
            }
        }
        ++i
    }
}
package com.example.klijent

import java.text.SimpleDateFormat
import java.util.*

class DateTimeUtil {
    companion object {
        private val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        fun formatDate(date: Date): String = simpleDateFormat.format(date)

        fun realMinutesToString(realMinutes: Double): String {
            val minutes = realMinutes.toInt()
            val seconds = (60 * (realMinutes - minutes)).toInt()
            return "${minutes}:${String.format("%02d", seconds)}"
        }

        fun millisToString(millis: Long): String {
            val milliseconds: Int = (millis % 1000 / 10).toInt()
            val seconds: Int = (millis / 1000 % 60).toInt()
            val minutes: Int = (millis / (1000 * 60) % 60).toInt()
            val hours: Int = (millis / (1000 * 60 * 60) % 24).toInt()

            return StringBuilder().apply {
                append(String.format("%02d", hours)).append(":")
                append(String.format("%02d", minutes)).append(":")
                append(String.format("%02d", seconds)).append(".")
                append(String.format("%02d", milliseconds))
            }.toString()
        }
    }
}
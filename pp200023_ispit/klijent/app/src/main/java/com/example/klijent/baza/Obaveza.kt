package com.example.klijent.baza

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "obaveza_table")
data class Obaveza(
    @PrimaryKey(autoGenerate = false) val id: Int = 0,
    @ColumnInfo(name = "fleg") val fleg: Boolean = false,
    @ColumnInfo(name = "naziv") val naziv: String = "",
    @ColumnInfo(name = "vreme") val vreme: Date = Date()
)
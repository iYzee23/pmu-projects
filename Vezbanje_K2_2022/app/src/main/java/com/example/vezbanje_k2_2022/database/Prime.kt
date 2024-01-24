package com.example.vezbanje_k2_2022.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "primes")
class Prime(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "leader") val leader: Int,
    @ColumnInfo(name = "first") val first: Int,
    @ColumnInfo(name = "second") val second: Int,
    @ColumnInfo(name = "third") val third: Int
)
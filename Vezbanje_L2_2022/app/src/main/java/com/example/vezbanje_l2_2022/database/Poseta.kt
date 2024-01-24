package com.example.vezbanje_l2_2022.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "poseta")
class Poseta(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "destinacija") val destinacija: String
)
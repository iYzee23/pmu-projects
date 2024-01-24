package com.example.vezba_l2.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "zapisi")
class Zapis (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "redni")val redniBrojPosete: Int,
    @ColumnInfo(name = "ime") val imeDestinacije: String
)
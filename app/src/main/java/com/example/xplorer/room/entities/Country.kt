package com.example.xplorer.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "countries")
data class Country(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val info: String
)
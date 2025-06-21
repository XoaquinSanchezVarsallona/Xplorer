package com.example.xplorer.room.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "favorites",
    foreignKeys = [
        ForeignKey(
            entity = Country::class,
            parentColumns = ["id"],
            childColumns = ["countryId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["countryId"])]
)
data class Favorite(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val countryId: Int
)
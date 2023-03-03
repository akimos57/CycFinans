package ru.cyclone.cycfinans.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "promotion")
data class Promotion(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
//    val time: Time,
    val type: Boolean,
    val category: String,
    val colorCategory: Int,
    val price: Int
)

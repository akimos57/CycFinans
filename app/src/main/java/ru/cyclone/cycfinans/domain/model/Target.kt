package ru.cyclone.cycfinans.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "target")
data class Target(
    @PrimaryKey(autoGenerate = true)
    val id:Long,
    val title:String,
)
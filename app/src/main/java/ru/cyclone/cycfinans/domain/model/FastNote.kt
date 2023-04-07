package ru.cyclone.cycfinans.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Time

@Entity(tableName = "fast_notes")
data class FastNote(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val content: String,
    val time: Time,
    val isCompleted: Boolean = false
)

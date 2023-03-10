package ru.cyclone.cycfinans.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import java.sql.Time

@Entity(tableName = "promotion")
data class Promotion(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val time: Time,
    val type: Boolean,
    val category: String,
    val colorCategory: Int,
    val price: Int
)

object TimeConverter {
    @TypeConverter
    fun toDate(timeLong: Long?): Time? {
        return timeLong?.let { Time(it) }
    }
    @TypeConverter
    fun fromTime(time: Time?): Long? {
        return time?.time
    }
}
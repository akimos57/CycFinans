package ru.cyclone.cycfinans.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.cyclone.cycfinans.data.local.dao.PromotionRepositoryImpl
import ru.cyclone.cycfinans.domain.model.DateConverter
import ru.cyclone.cycfinans.domain.model.Promotion
import ru.cyclone.cycfinans.domain.model.TimeConverter

@Database(entities =
    [Promotion::class],
    version = 1)

@TypeConverters(DateConverter::class, TimeConverter::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun promotionDao(): PromotionRepositoryImpl
}
package ru.cyclone.cycfinans.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.cyclone.cycfinans.data.local.dao.PromotionRepositoryImpl
import ru.cyclone.cycfinans.domain.model.Promotion

@Database(entities =
    [Promotion::class],
    version = 1)

abstract class AppDatabase: RoomDatabase() {
    abstract fun promotionDao(): PromotionRepositoryImpl
}
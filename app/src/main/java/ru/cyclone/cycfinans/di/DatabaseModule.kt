package ru.cyclone.cycfinans.di

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.cyclone.cycfinans.data.local.AppDatabase
import ru.cyclone.cycfinans.data.local.dao.FastNoteRepositoryImpl
import ru.cyclone.cycfinans.data.local.dao.NoteRepositoryImpl
import ru.cyclone.cycfinans.data.local.dao.PromotionRepositoryImpl
import ru.cyclone.cycnote.R
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {
    @Provides
    fun provideAppDate(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            context = appContext,
            klass = AppDatabase::class.java,
            name = "finance_database"
        ).build()
    }

    @Provides
    fun provideTransactionDao(appDatabase: AppDatabase): PromotionRepositoryImpl {
        return appDatabase.promotionDao()
    }

    @Provides
    fun provideNoteDao(appDatabase: AppDatabase): NoteRepositoryImpl{
        return appDatabase.noteDao()
    }

    @Provides
    fun provideFastNoteDao(appDatabase: AppDatabase): FastNoteRepositoryImpl {
        return appDatabase.fastNoteDao()
    }
}










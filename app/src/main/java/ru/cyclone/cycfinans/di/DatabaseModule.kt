package ru.cyclone.cycfinans.di

import android.content.Context
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

    @Provides
    fun provideAppContext(context: Context) : Context = context
}










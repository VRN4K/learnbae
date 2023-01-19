package com.learnbae.my.di

import android.content.Context
import androidx.room.Room
import com.learnbae.my.data.storage.VocabularyDataBase
import com.learnbae.my.data.storage.dao.VocabularyDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Provides
    fun provideVocabularyDao(vocabularyDataBase: VocabularyDataBase): VocabularyDao {
        return vocabularyDataBase.VocabularyDao()
    }

    @Provides
    @Singleton
    fun provideRoomDatabase(@ApplicationContext context: Context): VocabularyDataBase {
        return Room.databaseBuilder(
            context,
            VocabularyDataBase::class.java,
            "learnbae-database"
        ).fallbackToDestructiveMigration()
            .build()
    }
}
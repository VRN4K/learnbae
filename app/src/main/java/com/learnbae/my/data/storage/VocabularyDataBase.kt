package com.learnbae.my.data.storage

import androidx.room.Database
import androidx.room.RoomDatabase
import com.learnbae.my.data.storage.dao.VocabularyDao
import com.learnbae.my.data.storage.entities.WordEntity

@Database(entities = [WordEntity::class], version = 1)
abstract class VocabularyDataBase : RoomDatabase() {
    abstract fun VocabularyDao(): VocabularyDao
}
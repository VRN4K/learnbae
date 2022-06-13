package com.learnbae.my.data.storage.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.learnbae.my.data.storage.entities.WordEntity

@Dao
interface VocabularyDao {

    @Insert
    fun addWordToVocabulary(wordEntity: WordEntity)

    @Query("SELECT * FROM vocabulary")
    fun getAllWords(): List<WordEntity>?

    @Query("DELETE FROM vocabulary WHERE id = :wordId")
    fun deleteWordById(wordId: String)

    //TODO()
//    @Query("UPDATE vocabulary SET WHERE id = :wordId")
//    fun updateWordById(wordId: String)
}
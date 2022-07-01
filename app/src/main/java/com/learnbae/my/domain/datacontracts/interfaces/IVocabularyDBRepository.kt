package com.learnbae.my.domain.datacontracts.interfaces

import com.learnbae.my.data.storage.entities.WordEntity

interface IVocabularyDBRepository {
    fun addWordToVocabulary(word: WordEntity)
    fun deleteWordById(wordId: String)
    fun getAllWords(): List<WordEntity>?
    fun getWordsCount(): Int
}
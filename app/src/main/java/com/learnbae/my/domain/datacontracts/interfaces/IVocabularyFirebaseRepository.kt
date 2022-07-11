package com.learnbae.my.domain.datacontracts.interfaces

import com.learnbae.my.domain.datacontracts.model.VocabularyWordUI

interface IVocabularyFirebaseRepository {
    fun addNewWord(userId: String, word: VocabularyWordUI)
    fun removeWord(userId: String, wordId: String)
    fun removeAllWords(userId: String)
    suspend fun getAllWordsId(userId: String): List<String>?
    suspend fun getWordsCount(userId: String): Int
    suspend fun getAllWords(userId: String): List<VocabularyWordUI>
    suspend fun synchronizeWords(userId: String, wordsList: List<VocabularyWordUI>)
}
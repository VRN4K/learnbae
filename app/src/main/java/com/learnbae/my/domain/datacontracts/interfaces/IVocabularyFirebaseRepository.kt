package com.learnbae.my.domain.datacontracts.interfaces

import com.learnbae.my.domain.datacontracts.model.VocabularyWordUI

interface IVocabularyFirebaseRepository {
    fun addNewWord(userId: String, word: VocabularyWordUI)
    fun removeWord(userId: String, wordId: String)
    suspend fun getAllWordsId(userId: String): List<String>?
    suspend fun getWordsCount(UserId: String): Int
    suspend fun getAllWords(UserId: String): List<VocabularyWordUI>
    suspend fun synchronizeWords(UserId: String, wordsList: List<VocabularyWordUI>)
}
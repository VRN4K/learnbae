package com.learnbae.my.domain.datacontracts.interfaces

import com.learnbae.my.domain.datacontracts.model.VocabularyWordUI

interface IVocabularyFirebaseRepository {
    fun addNewWord(userId: String, word: VocabularyWordUI)
    fun removeWord(userId: String, wordId: String)
    suspend fun getWordsCount(UserId: String):Int
}
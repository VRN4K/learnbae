package com.learnbae.my.domain.interfaces

import com.learnbae.my.domain.datacontracts.model.SearchResultUIModel
import com.learnbae.my.domain.datacontracts.model.VocabularyWordUI
import com.learnbae.my.domain.datacontracts.model.WordMinicardUI

interface ITranslationInteractor {
    suspend fun deleteWordById(userId: String? = null, wordId: String)
    suspend fun getAllWords(): List<VocabularyWordUI>
    suspend fun getAllRemoteWords(userId: String): List<VocabularyWordUI>
    suspend fun getWordOfADay(): WordMinicardUI
    suspend fun addWordToVocabulary(userId: String? = null, word: VocabularyWordUI)
    suspend fun isWordAlreadyInVocabulary(userId: String? = null, word: String): Boolean
    suspend fun getWordsCount(userId: String): Int
    suspend fun isWordsSynchronize(userId: String): Boolean
    suspend fun synchronizeWords(userId: String)
    suspend fun deleteAllWordsFromAccount(userId: String)
    suspend fun getWordTranslation(
        sourceLang: String,
        targetLang: String,
        word: String
    ): SearchResultUIModel

    fun getRemoteWordCount(): Int
    suspend fun getWordId(userId: String?, word: String): String?
    suspend fun deleteWordByTitle(userId: String?, word: String)
}
package com.learnbae.my.domain.interfaces

import com.learnbae.my.data.net.model.TranslationModel
import com.learnbae.my.data.net.model.WordMinicardModel
import com.learnbae.my.domain.datacontracts.model.VocabularyWordUI
import com.learnbae.my.domain.datacontracts.model.WordMinicardUI

interface ITranslationInteractor {
    suspend fun getWordMinicard(text: String): WordMinicardModel
    suspend fun getTranslation(text: String): TranslationModel
    suspend fun deleteWordById(userId: String? = null, wordId: String)
    suspend fun getAllWords(): List<VocabularyWordUI>
    suspend fun getWordAudio(word: String): String?
    suspend fun getWordOfADay(date: String): WordMinicardUI
    suspend fun getAuthKey(): String
    suspend fun addWordToVocabulary(userId: String? = null, word: VocabularyWordUI)
}
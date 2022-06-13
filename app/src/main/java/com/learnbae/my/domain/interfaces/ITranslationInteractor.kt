package com.learnbae.my.domain.interfaces

import com.learnbae.my.data.net.model.TranslationModel
import com.learnbae.my.domain.datacontracts.model.VocabularyWordUI
import com.learnbae.my.domain.datacontracts.model.WordMinicardUI

interface ITranslationInteractor {
    suspend fun getWordMinicard(
        text: String
    ): WordMinicardUI

    suspend fun getTranslation(text: String): TranslationModel

    suspend fun addWordToVocabulary(word: VocabularyWordUI)
    suspend fun deleteWordById(wordId: String)
    suspend fun getAllWords(): List<VocabularyWordUI>
}
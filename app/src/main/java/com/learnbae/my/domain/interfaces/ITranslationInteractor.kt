package com.learnbae.my.domain.interfaces

import com.learnbae.my.data.model.Translation
import com.learnbae.my.data.model.TranslationModel
import com.learnbae.my.domain.datacontracts.model.WordMinicardUI

interface ITranslationInteractor {
    suspend fun getWordMinicard(
        text: String
    ): WordMinicardUI

    suspend fun getTranslation(text: String): TranslationModel
}
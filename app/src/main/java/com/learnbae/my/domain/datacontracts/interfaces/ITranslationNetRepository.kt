package com.learnbae.my.domain.datacontracts.interfaces

import com.learnbae.my.data.net.model.TranslationModel
import com.learnbae.my.data.net.model.WordMinicardModel

interface ITranslationNetRepository {
    suspend fun getMinicard(
        text: String,
        srcLang: String,
        dstLang: String
    ): WordMinicardModel

    suspend fun getTranslation(
        text: String,
        srcLang: String,
        dstLang: String
    ): TranslationModel
}
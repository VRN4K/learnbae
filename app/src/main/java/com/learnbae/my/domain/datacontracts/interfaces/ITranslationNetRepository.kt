package com.learnbae.my.domain.datacontracts.interfaces

import com.learnbae.my.data.net.model.TranslationModel
import com.learnbae.my.data.net.model.WordMinicardModel
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

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

    suspend fun getWordSound(
        dictionaryName: String,
        fileName: String,
    ): String

    suspend fun getAuthKey(): String
}
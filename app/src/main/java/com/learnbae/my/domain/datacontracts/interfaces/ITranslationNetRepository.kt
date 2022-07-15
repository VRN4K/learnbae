package com.learnbae.my.domain.datacontracts.interfaces

import com.learnbae.my.data.net.model.SearchResultModel

interface ITranslationNetRepository {
    suspend fun getWordTranslation(
        sourceLang: String,
        targetLang: String,
        word: String
    ): SearchResultModel
}
package com.learnbae.my.data.net.repository

import com.learnbae.my.data.net.model.SearchResultModel
import com.learnbae.my.data.net.retrofit.DictionaryService
import com.learnbae.my.domain.datacontracts.interfaces.ITranslationNetRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TranslationNetRepository @Inject constructor(private val mService: DictionaryService) :
    ITranslationNetRepository {

    override suspend fun getWordTranslation(
        sourceLang: String,
        targetLang: String,
        word: String
    ): SearchResultModel {
        return mService.getWordTranslation(sourceLang, targetLang, word, false)
    }
}


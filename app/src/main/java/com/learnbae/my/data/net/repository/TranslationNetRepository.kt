package com.learnbae.my.data.net.repository

import com.learnbae.my.data.net.model.TranslationModel
import com.learnbae.my.data.net.model.WordMinicardModel
import com.learnbae.my.data.net.retrofit.TranslationService
import com.learnbae.my.domain.datacontracts.interfaces.ITranslationNetRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TranslationNetRepository @Inject constructor(private val mService: TranslationService) :
    ITranslationNetRepository {

    override suspend fun getMinicard(
        text: String,
        srcLang: String,
        dstLang: String
    ): WordMinicardModel {
        return mService.getMinicard(text, srcLang, dstLang)
    }

    override suspend fun getTranslation(
        text: String,
        srcLang: String,
        dstLang: String
    ): TranslationModel {
        return mService.getTranslation(text, srcLang, dstLang).first()
    }

    override suspend fun getWordSound(dictionaryName: String, fileName: String): String {
        return mService.getWordSound(dictionaryName, fileName)
    }

    override suspend fun getAuthKey(): String {
        return mService.getAuthKey()
    }
}


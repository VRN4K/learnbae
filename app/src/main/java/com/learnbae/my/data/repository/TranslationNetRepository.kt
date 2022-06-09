package com.learnbae.my.data.repository

import android.content.res.Resources
import com.learnbae.my.R
import com.learnbae.my.data.model.TranslationModel
import com.learnbae.my.data.model.WordMinicardModel
import com.learnbae.my.data.retrofit.RetrofitInstance
import com.learnbae.my.data.retrofit.TranslationService
import com.learnbae.my.domain.datacontracts.interfaces.ITranslationNetRepository
import com.learnbae.my.domain.datacontracts.model.WordMinicardUI
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class TranslationNetRepository : ITranslationNetRepository, KoinComponent {
    private val mService: TranslationService by inject()

    override suspend fun getMinicard(
        text: String,
        srcLang: String,
        dstLang: String
    ): WordMinicardModel {
        val authKey = "Bearer ${mService.getAuthKey(RetrofitInstance.BASE_API_KEY)}"
        return mService.getMinicard(authKey, text, srcLang, dstLang)
    }

    override suspend fun getTranslation(
        text: String,
        srcLang: String,
        dstLang: String
    ): TranslationModel {
        val authKey = "Bearer ${mService.getAuthKey(RetrofitInstance.BASE_API_KEY)}"
        return mService.getTranslation(authKey, text, srcLang, dstLang).first()
    }
}

fun WordMinicardModel.toUI(resources: Resources, translation: TranslationModel): WordMinicardUI {
    return WordMinicardUI(
        this.translation.heading.replaceFirstChar { it.uppercase() },
        resources.getString(
            R.string.transcription_pattern,
            translation.Body.first().Markup.first().Text
        ),
        listOf(this.translation.translation),
        this.translation.soundName
    )
}
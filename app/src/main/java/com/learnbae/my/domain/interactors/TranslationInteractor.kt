package com.learnbae.my.domain.interactors

import android.content.res.Resources
import com.learnbae.my.data.model.TranslationModel
import com.learnbae.my.data.repository.toUI
import com.learnbae.my.data.retrofit.LanguagesCodes
import com.learnbae.my.domain.datacontracts.interfaces.ITranslationNetRepository
import com.learnbae.my.domain.datacontracts.model.WordMinicardUI
import com.learnbae.my.domain.interfaces.ITranslationInteractor

class TranslationInteractor(
    private val netRepository: ITranslationNetRepository,
    private val resources: Resources
) : ITranslationInteractor {

    override suspend fun getWordMinicard(
        text: String
    ): WordMinicardUI {
        return netRepository.getMinicard(text, LanguagesCodes.EN, LanguagesCodes.RU)
            .toUI(resources, getTranslation(text))
    }

    override suspend fun getTranslation(text: String): TranslationModel {
        return netRepository.getTranslation(text, LanguagesCodes.EN, LanguagesCodes.RU)
    }
}

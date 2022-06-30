package com.learnbae.my.domain.interactors

import android.content.res.Resources
import com.learnbae.my.data.net.model.TranslationModel
import com.learnbae.my.data.net.model.WordMinicardModel
import com.learnbae.my.data.net.model.toUI
import com.learnbae.my.data.net.retrofit.LanguagesCodes
import com.learnbae.my.data.storage.entities.toEntity
import com.learnbae.my.data.storage.entities.toUI
import com.learnbae.my.domain.datacontracts.interfaces.ITranslationNetRepository
import com.learnbae.my.domain.datacontracts.interfaces.IVocabularyDBRepository
import com.learnbae.my.domain.datacontracts.interfaces.IVocabularyNetRepository
import com.learnbae.my.domain.datacontracts.model.VocabularyWordUI
import com.learnbae.my.domain.datacontracts.model.WordMinicardUI
import com.learnbae.my.domain.interfaces.ITranslationInteractor

class TranslationInteractor(
    private val netRepository: ITranslationNetRepository,
    private val dbRepository: IVocabularyDBRepository,
    private val vocabularyNetRepository: IVocabularyNetRepository,
    private val resources: Resources
) : ITranslationInteractor {

    override suspend fun getWordAudio(word: String): String? {
        return vocabularyNetRepository.getWordAudio(word)
    }

    override suspend fun getWordOfADay(date: String): WordMinicardUI {
        val wordOfADay = vocabularyNetRepository.getWordOfADay(date)
        val soundUrl = getWordAudio(wordOfADay.word)
        return getWordMinicard(wordOfADay.word).toUI(
            resources, getTranslation(wordOfADay.word),
            wordOfADay.definitions.first().partOfSpeech,
            soundUrl
        )
    }

    override suspend fun getWordMinicard(
        text: String
    ): WordMinicardModel {
        return netRepository.getMinicard(text, LanguagesCodes.EN, LanguagesCodes.RU)
    }

    override suspend fun getTranslation(text: String): TranslationModel {
        return netRepository.getTranslation(text, LanguagesCodes.EN, LanguagesCodes.RU)
    }

    override suspend fun addWordToVocabulary(word: VocabularyWordUI) {
        dbRepository.addWordToVocabulary(word.toEntity())
    }

    override suspend fun deleteWordById(wordId: String) {
        dbRepository.deleteWordById(wordId)
    }

    override suspend fun getAllWords(): List<VocabularyWordUI> {
        return dbRepository.getAllWords()?.map { it.toUI() } ?: emptyList()
    }

    override suspend fun getAuthKey(): String {
        return netRepository.getAuthKey()
    }
}

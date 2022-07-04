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
import com.learnbae.my.domain.datacontracts.interfaces.IVocabularyFirebaseRepository
import com.learnbae.my.domain.datacontracts.interfaces.IVocabularyNetRepository
import com.learnbae.my.domain.datacontracts.model.VocabularyWordUI
import com.learnbae.my.domain.datacontracts.model.WordMinicardUI
import com.learnbae.my.domain.interfaces.ITranslationInteractor

class TranslationInteractor(
    private val netRepository: ITranslationNetRepository,
    private val dbRepository: IVocabularyDBRepository,
    private val vocabularyNetRepository: IVocabularyNetRepository,
    private val vocabularyFirebaseRepository: IVocabularyFirebaseRepository,
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

    override suspend fun addWordToVocabulary(userId: String?, word: VocabularyWordUI) {
        dbRepository.addWordToVocabulary(word.toEntity())
        userId?.let { vocabularyFirebaseRepository.addNewWord(userId, word) }
    }

    override suspend fun deleteWordById(userId: String?, wordId: String) {
        dbRepository.deleteWordById(wordId)
        userId?.let { vocabularyFirebaseRepository.removeWord(userId, wordId) }
    }

    override suspend fun getAllWords(): List<VocabularyWordUI> {
        return dbRepository.getAllWords()?.map { it.toUI() } ?: emptyList()
    }

    override suspend fun getAllRemoteWords(userId: String): List<VocabularyWordUI> {
        vocabularyFirebaseRepository.getAllWords(userId)
        return emptyList()
    }

    override suspend fun getWordsCount(userId: String): Int {
        return vocabularyFirebaseRepository.getWordsCount(userId)
    }

    override suspend fun isWordsSynchronize(userId: String): Boolean {
        val localWord = getAllWords()
        val remoteWords = vocabularyFirebaseRepository.getAllWordsId(userId)

        if (!localWord.isNullOrEmpty() && !remoteWords.isNullOrEmpty()){
            remoteWords.minus(localWord.map { word -> word.id })

            }
        return false
    }

    override suspend fun synchronizeWords(userId: String) {
        val localWord = getAllWords()
        val remoteWords = vocabularyFirebaseRepository.getAllWords(userId)

        localWord.union(remoteWords)
    }

    override suspend fun getAuthKey(): String {
        return netRepository.getAuthKey()
    }
}

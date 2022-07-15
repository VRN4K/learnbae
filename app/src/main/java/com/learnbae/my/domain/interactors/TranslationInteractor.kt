package com.learnbae.my.domain.interactors

import android.content.res.Resources
import com.learnbae.my.R
import com.learnbae.my.data.storage.entities.toEntity
import com.learnbae.my.data.storage.entities.toUI
import com.learnbae.my.domain.datacontracts.interfaces.ITranslationNetRepository
import com.learnbae.my.domain.datacontracts.interfaces.IVocabularyDBRepository
import com.learnbae.my.domain.datacontracts.interfaces.IVocabularyFirebaseRepository
import com.learnbae.my.domain.datacontracts.model.SearchResultUIModel
import com.learnbae.my.domain.datacontracts.model.VocabularyWordUI
import com.learnbae.my.domain.datacontracts.model.WordMinicardUI
import com.learnbae.my.domain.datacontracts.model.toUI
import com.learnbae.my.domain.interfaces.ITranslationInteractor
import com.learnbae.my.presentation.common.WordOfADayParser
import javax.inject.Inject

class TranslationInteractor @Inject constructor(
    private val netRepository: ITranslationNetRepository,
    private val dbRepository: IVocabularyDBRepository,
    private val vocabularyFirebaseRepository: IVocabularyFirebaseRepository,
    private val resources: Resources
) : ITranslationInteractor {

    override suspend fun getWordOfADay(): WordMinicardUI {
        val wordOfADay = getWordTranslation("en", "ru", WordOfADayParser.getWordOfADay())
        return WordMinicardUI(
            wordOfADay.word,
            resources.getString(R.string.transcription_pattern, wordOfADay.pronunciations),
            wordOfADay.translation,
            wordOfADay.wordSounds?.let { it.entries.find { it.value != null }?.value },
        )
    }

    override suspend fun addWordToVocabulary(userId: String?, word: VocabularyWordUI) {
        dbRepository.addWordToVocabulary(word.toEntity())
        userId?.let { vocabularyFirebaseRepository.addNewWord(userId, word) }
    }

    override suspend fun isWordAlreadyInVocabulary(userId: String?, word: String): Boolean {
        if (getAllWords().find { it.title.lowercase() == word.lowercase() } != null) {
            userId?.let { userId ->
                if (getAllRemoteWords(userId).find { it.title.lowercase() == word.lowercase() } != null) {
                    return true
                }
            }
            return true
        }
        return false
    }

    override suspend fun deleteWordById(userId: String?, wordId: String) {
        dbRepository.deleteWordById(wordId)
        userId?.let { vocabularyFirebaseRepository.removeWord(userId, wordId) }
    }

    override suspend fun getAllWords(): List<VocabularyWordUI> {
        return dbRepository.getAllWords()?.map { it.toUI() } ?: emptyList()
    }

    override suspend fun getAllRemoteWords(userId: String): List<VocabularyWordUI> {
        return vocabularyFirebaseRepository.getAllWords(userId)
    }

    override suspend fun deleteWordByTitle(userId: String?, word: String) {
        val wordId = getWordId(userId, word)
        wordId?.let { deleteWordById(userId, it) }
    }

    override suspend fun getWordsCount(userId: String): Int {
        return vocabularyFirebaseRepository.getWordsCount(userId)
    }

    override suspend fun isWordsSynchronize(userId: String): Boolean {
        val localWord = getAllWords()
        val remoteWords = vocabularyFirebaseRepository.getAllWordsId(userId)

        if (!localWord.isNullOrEmpty() && !remoteWords.isNullOrEmpty()) {
            remoteWords.minus(localWord.map { word -> word.id })
        }
        return false
    }

    override suspend fun synchronizeWords(userId: String) {
        val localWord = getAllWords()
        val remoteWords = vocabularyFirebaseRepository.getAllWords(userId)
        val wordsSet = localWord.toMutableSet().apply {
            addAll(remoteWords)
        }.distinctBy { it.id }

        vocabularyFirebaseRepository.synchronizeWords(userId, wordsSet.toList().minus(remoteWords))
        dbRepository.synchronizeWords(wordsSet.toList().minus(localWord).map { it.toEntity() })
    }

    override suspend fun deleteAllWordsFromAccount(userId: String) {
        vocabularyFirebaseRepository
    }

    override suspend fun getWordId(userId: String?, word: String): String? {
        var wordId: String? = getAllWords().find { it.title.lowercase() == word.lowercase() }?.id

        if (wordId != null) {
            return wordId
        } else {
            userId?.let {
                wordId = getAllRemoteWords(it).find { it.title.lowercase() == word.lowercase() }?.id
            }
        }
        return wordId
    }

    override suspend fun getWordTranslation(
        sourceLang: String,
        targetLang: String,
        word: String
    ): SearchResultUIModel {
        return netRepository.getWordTranslation(sourceLang, targetLang, word).toUI()
    }
}

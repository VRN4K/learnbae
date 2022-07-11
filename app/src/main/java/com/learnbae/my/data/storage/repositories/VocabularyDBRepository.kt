package com.learnbae.my.data.storage.repositories

import com.learnbae.my.data.storage.VocabularyDataBase
import com.learnbae.my.data.storage.entities.WordEntity
import com.learnbae.my.domain.datacontracts.interfaces.IVocabularyDBRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VocabularyDBRepository @Inject constructor(private val dbVocabulary: VocabularyDataBase) :
    IVocabularyDBRepository {

    override fun addWordToVocabulary(word: WordEntity) {
        dbVocabulary.VocabularyDao().addWordToVocabulary(word)
    }

    override fun deleteWordById(wordId: String) {
        dbVocabulary.VocabularyDao().deleteWordById(wordId)
    }

    override fun getAllWords(): List<WordEntity>? {
        return dbVocabulary.VocabularyDao().getAllWords()
    }

    override fun getWordsCount(): Int {
        return dbVocabulary.VocabularyDao().getWordsCount()
    }

    override fun synchronizeWords(wordsList: List<WordEntity>) {
        wordsList.onEach {
            addWordToVocabulary(it)
        }
    }
}
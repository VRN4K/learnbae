package com.learnbae.my.data.storage.repositories

import com.learnbae.my.data.storage.VocabularyDataBase
import com.learnbae.my.data.storage.entities.WordEntity
import com.learnbae.my.domain.datacontracts.interfaces.IVocabularyDBRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class VocabularyDBRepository : IVocabularyDBRepository, KoinComponent {
    private val dbVocabulary: VocabularyDataBase by inject()

    override fun addWordToVocabulary(word: WordEntity) {
        dbVocabulary.VocabularyDao().addWordToVocabulary(word)
    }

    override fun deleteWordById(wordId: String) {
        dbVocabulary.VocabularyDao().deleteWordById(wordId)
    }

    override fun getAllWords(): List<WordEntity>? {
       return dbVocabulary.VocabularyDao().getAllWords()
    }
}
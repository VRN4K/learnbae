package com.learnbae.my.data.net.repository

import com.learnbae.my.data.net.model.WordOfADayModel
import com.learnbae.my.data.net.retrofit.RetrofitInstance.VOCABULARY_API_KEY
import com.learnbae.my.data.net.retrofit.VocabularyService
import com.learnbae.my.domain.datacontracts.interfaces.IVocabularyNetRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class VocabularyNetRepository : IVocabularyNetRepository, KoinComponent {
    companion object {
        private const val LIMIT = "5"
        private const val USE_CANONICAL = "true"
    }

    private val mService: VocabularyService by inject()
    override suspend fun getWordOfADay(date: String): WordOfADayModel {
        return mService.getWordOfADay(date, VOCABULARY_API_KEY)
    }

    override suspend fun getWordAudio(word: String): String? {
        return with(mService.getWordAudio(word, USE_CANONICAL, LIMIT, VOCABULARY_API_KEY)) {
            this?.let { if (this.size > 1) this.last().fileURL else this.first().fileURL }
        }
    }
}
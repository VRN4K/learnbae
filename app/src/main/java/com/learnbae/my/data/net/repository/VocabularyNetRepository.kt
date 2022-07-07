package com.learnbae.my.data.net.repository

import com.learnbae.my.data.net.model.WordOfADayModel
import com.learnbae.my.data.net.retrofit.RetrofitInstance.Companion.VOCABULARY_API_KEY
import com.learnbae.my.data.net.retrofit.VocabularyService
import com.learnbae.my.domain.datacontracts.interfaces.IVocabularyNetRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VocabularyNetRepository @Inject constructor(private val mService: VocabularyService) : IVocabularyNetRepository{
    companion object {
        private const val LIMIT = "5"
        private const val USE_CANONICAL = "true"
    }

    override suspend fun getWordOfADay(date: String): WordOfADayModel {
        return mService.getWordOfADay(date, VOCABULARY_API_KEY)
    }

    override suspend fun getWordAudio(word: String): String? {
        return with(mService.getWordAudio(word, USE_CANONICAL, LIMIT, VOCABULARY_API_KEY)) {
            this?.let { if (this.size > 1) this.last().fileURL else this.first().fileURL }
        }
    }
}
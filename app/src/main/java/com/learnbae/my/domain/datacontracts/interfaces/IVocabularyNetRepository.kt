package com.learnbae.my.domain.datacontracts.interfaces

import com.learnbae.my.data.net.model.WordOfADayModel

interface IVocabularyNetRepository {
    suspend fun getWordOfADay(date: String): WordOfADayModel
    suspend fun getWordAudio(word: String): String?
}
package com.learnbae.my.data.net.retrofit

import com.learnbae.my.data.net.model.AudioModel
import com.learnbae.my.data.net.model.WordOfADayModel
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface VocabularyService {

    @GET("words.json/wordOfTheDay")
    suspend fun getWordOfADay(
        @Query("date") date: String,
        @Query("api_key") apiKey: String
    ): WordOfADayModel

    @GET("word.json/{word}/audio")
    suspend fun getWordAudio(
        @Path("word") word: String,
        @Query("useCanonical") useCanonical: String,
        @Query("limit") limit: String,
        @Query("api_key") apiKey: String
    ): List<AudioModel>?
}
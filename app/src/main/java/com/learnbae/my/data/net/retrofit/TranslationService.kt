package com.learnbae.my.data.net.retrofit

import com.learnbae.my.data.net.model.TranslationModel
import com.learnbae.my.data.net.model.WordMinicardModel
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface TranslationService {
    @POST("Authenticate")
    suspend fun getAuthKey(
        @Header("Authorization") token:String,
    ): String

    @GET("Translation")
    suspend fun getTranslation(
        @Query("text") text: String,
        @Query("srcLang") srcLang: String,
        @Query("dstLang") dstLang: String,
    ): List<TranslationModel>

    @GET("Minicard")
    suspend fun getMinicard(
        @Query("text") text: String,
        @Query("srcLang") srcLang: String,
        @Query("dstLang") dstLang: String
    ): WordMinicardModel

    @GET("Sound")
    suspend fun getWordSound(
    @Query("dictionaryName") dictionaryName: String,
    @Query("fileName") fileName: String,
    ): String
}

object LanguagesCodes {
    const val EN = "1033"
    const val RU = "1049"
}
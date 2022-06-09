package com.learnbae.my.data.retrofit

import com.learnbae.my.data.model.TranslationModel
import com.learnbae.my.data.model.WordMinicardModel
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface TranslationService {
    @POST("authenticate")
    suspend fun getAuthKey(
        @Header("Authorization") token:String,
    ): String

    @GET("Translation")
    suspend fun getTranslation(
        @Header("Authorization") token:String,
        @Query("text") text: String,
        @Query("srcLang") srcLang: String,
        @Query("dstLang") dstLang: String,
    ): List<TranslationModel>

    @GET("Minicard")
    suspend fun getMinicard(
        @Header("Authorization") token:String,
        @Query("text") text: String,
        @Query("srcLang") srcLang: String,
        @Query("dstLang") dstLang: String
    ): WordMinicardModel
}

object LanguagesCodes {
    const val EN = "1033"
    const val RU = "1049"
}
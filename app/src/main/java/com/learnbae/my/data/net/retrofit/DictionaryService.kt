package com.learnbae.my.data.net.retrofit

import com.learnbae.my.data.net.model.SearchResultModel
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DictionaryService {
    @GET("translations/{source_lang_translate}/{target_lang_translate}/{word_id}")
    suspend fun getWordTranslation(
        @Path("source_lang_translate") sourceLang: String,
        @Path("target_lang_translate") targetLang: String,
        @Path("word_id") wordId: String,
        @Query("strictMatch") strictMatch: Boolean
    ): SearchResultModel
}

package com.learnbae.my.data.net.retrofit

import com.learnbae.my.di.InteractorModule
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class RetrofitInstance @Inject constructor(
    @InteractorModule.TranslatorClient private val okHttpClientTranslator: OkHttpClient,
    @InteractorModule.VocabularyClient private val okHttpClientVocabulary: OkHttpClient,
    @InteractorModule.DictionaryClient private val okHttpClientDictionary: OkHttpClient
) {
    companion object {
        private const val BASE_TRANSLATOR_URL = "https://developers.lingvolive.com/api/v1/"
        private const val BASE_TRANS_URL = "https://od-api.oxforddictionaries.com/api/v2/"
        const val APP_ID = "f443e2dc"
        const val APP_KEY = "7beca55280bf5cd36cac32b6d0c8e3d5"
        private const val BASE_VOCABULARY_URL = "http://api.wordnik.com/v4/"
        const val BASIC_API_KEY =
            "NzFlMTEzMmUtNmFhNy00MmEzLWE0MDktMWY1NTIwYzRhZmFmOmNkYzcyMjMwOTBmMzQ3OTdhOTdiNzhkZWM4NmI4YjYz"
        const val VOCABULARY_API_KEY = "7bsshqwv0yviklvx4s0jrryg07v0xirqifeakx1bcftg2h5vp"
    }

    val retrofitTranslator: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_TRANSLATOR_URL)
            .client(okHttpClientTranslator)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val retrofitNewTranslator: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_TRANS_URL)
            .client(okHttpClientDictionary)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val retrofitVocabulary: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_VOCABULARY_URL)
            .client(okHttpClientVocabulary)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}
package com.learnbae.my.data.net.retrofit

import okhttp3.OkHttpClient
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.qualifier.named
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance : KoinComponent {
    private const val BASE_TRANSLATOR_URL = "https://developers.lingvolive.com/api/v1/"
    private const val BASE_VOCABULARY_URL = "http://api.wordnik.com/v4/"
    const val BASIC_API_KEY =
        "NzFlMTEzMmUtNmFhNy00MmEzLWE0MDktMWY1NTIwYzRhZmFmOmNkYzcyMjMwOTBmMzQ3OTdhOTdiNzhkZWM4NmI4YjYz"
    const val VOCABULARY_API_KEY = "7bsshqwv0yviklvx4s0jrryg07v0xirqifeakx1bcftg2h5vp"

    private val okHttpClient: OkHttpClient by inject(qualifier = named("TranslatorClient"))
    private val okHttpClientVocabulary: OkHttpClient by inject(qualifier = named("VocabularyClient"))

    val retrofitTranslator: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_TRANSLATOR_URL)
            .client(okHttpClient)
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
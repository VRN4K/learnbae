package com.learnbae.my.data.net.retrofit

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val BASE_URL = "https://developers.lingvolive.com/api/v1/"
    const val BASE_API_KEY = "Basic NzFlMTEzMmUtNmFhNy00MmEzLWE0MDktMWY1NTIwYzRhZmFmOmNkYzcyMjMwOTBmMzQ3OTdhOTdiNzhkZWM4NmI4YjYz"

    private val okHttpClient = OkHttpClient.Builder()
        .addNetworkInterceptor(HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BASIC)
            setLevel(HttpLoggingInterceptor.Level.BODY)
        })
        .build()

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getInstance(): Retrofit {
        return retrofit
    }
}
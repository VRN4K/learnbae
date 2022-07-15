package com.learnbae.my.data.net.retrofit

import com.learnbae.my.di.InteractorModule
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class RetrofitInstance @Inject constructor(
    @InteractorModule.DictionaryClient private val okHttpClientDictionary: OkHttpClient
) {
    companion object {
        private const val BASE_TRANSLATOR_URL = "https://od-api.oxforddictionaries.com/api/v2/"
        const val APP_ID = "f443e2dc"
        const val APP_KEY = "7beca55280bf5cd36cac32b6d0c8e3d5"
    }

    val retrofitTranslator: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_TRANSLATOR_URL)
            .client(okHttpClientDictionary)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}
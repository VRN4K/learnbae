package com.learnbae.my.data.net.retrofit

import com.learnbae.my.data.net.retrofit.RetrofitInstance.Companion.APP_ID
import com.learnbae.my.data.net.retrofit.RetrofitInstance.Companion.APP_KEY
import okhttp3.Interceptor
import okhttp3.Response

class DictionaryInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("Accept", "application/json")
            .addHeader("app_id", APP_ID)
            .addHeader("app_key", APP_KEY)
            .build()

        return chain.proceed(request)
    }
}
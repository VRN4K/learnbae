package com.learnbae.my.data.net.retrofit

import com.google.gson.Gson
import com.learnbae.my.data.net.retrofit.RetrofitInstance.Companion.BASIC_API_KEY
import com.learnbae.my.data.storage.preferences.StringPreference
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class VocabularyInterceptor @Inject constructor(private val sharedPreferences: StringPreference) : Interceptor,
    CoroutineScope {
    companion object {
        const val MILLIS_IN_ONE_DAY = 86_400_000
        const val authKeyStore = "AUTH_KEY_STORE"
        const val authKeyPair = "AUTH_KEY_MAP"
    }

    override val coroutineContext: CoroutineContext = Dispatchers.IO + SupervisorJob()

    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = if (chain.request().url.toString().contains("authenticate")) {
            chain.request().newBuilder()
                .addHeader("Authorization", "Basic $BASIC_API_KEY").build()
        } else {
            val authKey = Gson().fromJson(
                sharedPreferences.getValue(),
                Pair::class.java
            ).first

            chain.request().newBuilder()
                .addHeader(
                    "Authorization",
                    "Bearer $authKey"
                ).build()
        }

        return chain.proceed(request)
    }
}
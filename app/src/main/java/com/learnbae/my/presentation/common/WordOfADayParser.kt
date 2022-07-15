package com.learnbae.my.presentation.common

import com.squareup.okhttp.OkHttpClient
import com.squareup.okhttp.Request
import org.jsoup.Jsoup

object WordOfADayParser{
    private const val URL = "https://feeds.feedburner.com/OLD-WordOfTheDay"

    suspend fun getWordOfADay(): String {
            val request = Request.Builder().url(URL).build()
            val response = OkHttpClient().newCall(request).execute().body().string()
            val lastWordEntry = Jsoup.parse(response).select("entry").last().select("title")

        return lastWordEntry.text()
    }
}
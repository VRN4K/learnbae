package com.learnbae.my.domain.utils

class DeeplinkMapper {
    companion object {
        private const val LINK_TO_APP_PATTERN = "https://my.learnbae.com/"
    }

    fun parseFromLink(link: String): String {
        return when{
            link.contains("resetPassword") -> link.substringAfter("oobCode=").substringBefore("&apiKey")
            else -> ""
        }
    }
}

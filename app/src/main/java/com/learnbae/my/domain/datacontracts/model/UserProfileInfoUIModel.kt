package com.learnbae.my.domain.datacontracts.model

import android.graphics.Bitmap

data class UserProfileInfoUIModel(
    val username: String,
    val userFullName: String,
    val englishLevel: String,
    val email: String,
    val singUpDate: String,
    var wordsCount: String? = "0",
    var profilePhoto: Bitmap? = null
)


package com.learnbae.my.domain.datacontracts.model

import android.net.Uri

class UserProfileInfoUIModel(
    val username: String,
    val userFullName: String,
    val englishLevel: String,
    val email: String,
    val singUpDate: String,
    val wordsCount: String,
    val profilePhoto: Uri? = null
)
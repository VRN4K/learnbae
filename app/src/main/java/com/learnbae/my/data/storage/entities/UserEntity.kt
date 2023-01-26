package com.learnbae.my.data.storage.entities

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.net.Uri
import com.learnbae.my.R
import com.learnbae.my.domain.datacontracts.model.UserProfileInfoUIModel
import com.learnbae.my.presentation.common.uriToBitmap

data class UserEntity(
    val username: String,
    val userFullName: String,
    val englishLevel: String,
    val email: String,
    var singUpDate: String? = "",
    var wordCount: String? = null
)

data class RegisterUserInfo(
    val email: String,
    val password: String
)

data class RegisterRequestData(
    val userInfo: UserEntity,
    val registerUserInfo: RegisterUserInfo
)

fun UserEntity.toUI(
    context: Context,
    profilePhoto: Bitmap?
): UserProfileInfoUIModel {
    return UserProfileInfoUIModel(
        username = context.resources.getString(
            R.string.profile_account_username_pattern,
            this.username
        ),
        userFullName = this.userFullName,
        englishLevel = this.englishLevel,
        email = this.email,
        singUpDate = this.singUpDate!!,
        wordsCount = wordCount,
        profilePhoto = profilePhoto
    )
}

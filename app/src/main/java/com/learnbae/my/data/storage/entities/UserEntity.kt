package com.learnbae.my.data.storage.entities

import android.content.res.Resources
import android.net.Uri
import com.learnbae.my.R
import com.learnbae.my.domain.datacontracts.model.UserProfileInfoUIModel

data class UserEntity(
    val username: String,
    val userFullName: String,
    val englishLevel: String,
    val email: String,
    var singUpDate: String? = ""
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
    resources: Resources,
    wordsCount: String,
    profilePhoto: String?
): UserProfileInfoUIModel {
    return UserProfileInfoUIModel(
        String.format(
            resources.getString(R.string.profile_account_username_pattern),
            this.username
        ),
        this.userFullName,
        this.englishLevel,
        this.email,
        String.format(
            resources.getString(R.string.profile_account_register_date_pattern),
            this.singUpDate!!
        ),
        String.format(
            resources.getString(R.string.profile_account_words_count_pattern),
            wordsCount
        ),
        profilePhoto
    )
}

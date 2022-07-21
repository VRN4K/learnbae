package com.learnbae.my.domain.datacontracts.model

import com.learnbae.my.data.storage.entities.UpdateUserEntity

data class UserProfileInfoUIModel(
    val username: String,
    val userFullName: String,
    val englishLevel: String,
    val email: String,
    val singUpDate: String,
    val wordsCount: String,
    var profilePhoto: String? = null
)

fun UserProfileInfoUIModel.toUpdateUserEntity(): UpdateUserEntity {
    return UpdateUserEntity(
        this.username,
        this.userFullName,
        this.email,
        this.profilePhoto
    )
}
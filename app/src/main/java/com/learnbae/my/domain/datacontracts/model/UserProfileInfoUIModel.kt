package com.learnbae.my.domain.datacontracts.model

import android.net.Uri
import androidx.room.Update
import com.learnbae.my.data.storage.entities.UpdateUserEntity
import com.learnbae.my.data.storage.entities.UserEntity

class UserProfileInfoUIModel(
    val username: String,
    val userFullName: String,
    val englishLevel: String,
    val email: String,
    val singUpDate: String,
    val wordsCount: String,
    val profilePhoto: Uri? = null
)

fun UserProfileInfoUIModel.toUpdateUserEntity(): UpdateUserEntity {
    return UpdateUserEntity(
        this.username,
        this.userFullName,
        this.email,
    )
}
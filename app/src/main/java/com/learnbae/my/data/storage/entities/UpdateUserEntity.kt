package com.learnbae.my.data.storage.entities

import android.graphics.Bitmap

data class UpdateUserEntity(
    var username: String? = null,
    var userFullName: String? = null,
    var email: String? = null,
    var profilePhoto: Bitmap? = null,
    var currentPassword: String? = null
)

data class PasswordChangeModel(
    val currentPassword: String,
    val newPassword: String
)
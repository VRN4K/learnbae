package com.learnbae.my.data.storage.entities

import android.net.Uri

data class UpdateUserEntity(
    var username: String? = null,
    var userFullName: String? = null,
    var email: String? = null,
    var profilePhoto: Uri? = null
)

data class PasswordChangeModel(
    val currentPassword: String,
    val newPassword: String
)
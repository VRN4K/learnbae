package com.learnbae.my.domain.datacontracts.model

import android.graphics.Bitmap
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class UserUpdateInformationUI(
    val username: String,
    val userFullName: String,
    val email: String,
    var profilePhoto: Bitmap? = null
) : Parcelable
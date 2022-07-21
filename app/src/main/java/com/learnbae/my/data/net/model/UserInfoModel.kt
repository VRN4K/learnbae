package com.learnbae.my.data.net.model

import com.google.gson.annotations.SerializedName

data class UserInfoModel(
    @SerializedName("englishLevel")
    val englishLevel: String,
    @SerializedName("singUpDate")
    val singUpDate: String,
    @SerializedName("userFullName")
    val userFullName: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("username")
    val username: String
)
package com.learnbae.my.data.storage.entities

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


package com.learnbae.my.domain.datacontracts.interfaces

import com.learnbae.my.data.storage.entities.RegisterUserInfo

interface IAuthRepository {
    suspend fun loginByEmailAndPassword(email: String, password: String): String?
    suspend fun logout()
    suspend fun registerNewUser(user: RegisterUserInfo): Boolean
}
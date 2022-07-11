package com.learnbae.my.domain.datacontracts.interfaces

import com.learnbae.my.data.storage.entities.RegisterUserInfo
import com.learnbae.my.data.storage.entities.UserEntity

interface IAuthRepository {
    suspend fun loginByEmailAndPassword(email: String, password: String): String?
    suspend fun logout()
    fun updateUserPassword(currentPassword: String, newPassword: String)
    fun sendResetPasswordCode()
    suspend fun deleteUser(): String?
    suspend fun registerNewUser(user: RegisterUserInfo): String?
    fun getUserId(): String?
}
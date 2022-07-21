package com.learnbae.my.domain.datacontracts.interfaces

import com.learnbae.my.data.storage.entities.RegisterUserInfo
import com.learnbae.my.data.storage.entities.UserEntity

interface IAuthRepository {
    suspend fun loginByEmailAndPassword(email: String, password: String): String?
    suspend fun logout()
    suspend fun resetPassword(code: String, newPassword: String)
    suspend fun updateUserPassword(currentPassword: String, newPassword: String)
    suspend fun deleteUser(): String?
    suspend fun registerNewUser(user: RegisterUserInfo): String?
    fun getUserId(): String?
    suspend fun isCodeValid(code: String): Boolean
    suspend fun sendEmailResetPasswordMessage(email: String)
    suspend fun updateUserEmail(newEmail: String)
}
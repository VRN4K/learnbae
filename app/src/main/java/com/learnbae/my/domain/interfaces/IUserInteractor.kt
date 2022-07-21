package com.learnbae.my.domain.interfaces

import android.graphics.Bitmap
import android.net.Uri
import com.learnbae.my.data.storage.entities.PasswordChangeModel
import com.learnbae.my.data.storage.entities.RegisterRequestData
import com.learnbae.my.data.storage.entities.UpdateUserEntity
import com.learnbae.my.domain.datacontracts.model.UserProfileInfoUIModel

interface IUserInteractor {
    suspend fun loginByEmailAndPassword(email: String, password: String)
    suspend fun isUserAuthorized(): Boolean
    suspend fun logout()
    suspend fun deleteAccount(): String?
    suspend fun isUsernameAvailable(username: String): Boolean
    suspend fun getUserInfo(wordsCount: Int): UserProfileInfoUIModel
    suspend fun registerNewUser(registerRequestData: RegisterRequestData)
    suspend fun uploadUserProfilePhoto(uri: Uri)
    suspend fun updateEnglishLevel(levelValue: String)
    fun getUserId(): String?
    suspend fun isCodeValid(code: String): Boolean
    suspend fun changeUserPassword(passwordChangeModel: PasswordChangeModel)
    suspend fun updateUserInfo(updateUserEntity: UpdateUserEntity)
    suspend fun resetPassword(code: String, newPassword: String)
    suspend fun sendEmailResetPasswordMessage(email: String)
}
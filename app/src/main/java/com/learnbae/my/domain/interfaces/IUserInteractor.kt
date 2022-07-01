package com.learnbae.my.domain.interfaces

import android.graphics.Bitmap
import android.net.Uri
import com.learnbae.my.data.storage.entities.RegisterRequestData
import com.learnbae.my.data.storage.entities.UserEntity
import com.learnbae.my.domain.datacontracts.model.UserProfileInfoUIModel

interface IUserInteractor {
    suspend fun loginByEmailAndPassword(email: String, password: String)
    suspend fun isUserAuthorized(): Boolean
    suspend fun logout()
    suspend fun getUserInfo(): UserProfileInfoUIModel
    suspend fun registerNewUser(registerRequestData: RegisterRequestData)
    suspend fun uploadUserProfilePhoto(uri: Uri? = null, bitmap: Bitmap? = null)
    suspend fun updateEnglishLevel(levelValue: String)
    suspend fun getUserId(): String?
}
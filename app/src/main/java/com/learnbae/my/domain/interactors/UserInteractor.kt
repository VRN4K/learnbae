package com.learnbae.my.domain.interactors

import android.annotation.SuppressLint
import android.content.res.Resources
import android.graphics.Bitmap
import android.net.Uri
import com.learnbae.my.data.storage.entities.RegisterRequestData
import com.learnbae.my.data.storage.entities.toUI
import com.learnbae.my.domain.datacontracts.interfaces.IAuthRepository
import com.learnbae.my.domain.datacontracts.interfaces.IAuthorizationStorageRepository
import com.learnbae.my.domain.datacontracts.interfaces.IStorageRepository
import com.learnbae.my.domain.datacontracts.interfaces.IUserDBRepository
import com.learnbae.my.domain.datacontracts.model.UserProfileInfoUIModel
import com.learnbae.my.domain.interfaces.IUserInteractor
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@SuppressLint("SimpleDateFormat")
class UserInteractor @Inject constructor(
    private val authRepository: IAuthRepository,
    private val authPreferenceRepository: IAuthorizationStorageRepository,
    private val storageRepository: IStorageRepository,
    private val userDBRepository: IUserDBRepository,
    private val resources: Resources
) : IUserInteractor {
    private val dateFormat = SimpleDateFormat("dd.MM.yyyy")

    override suspend fun loginByEmailAndPassword(email: String, password: String) {
        val token = authRepository.loginByEmailAndPassword(email, password)
        authPreferenceRepository.saveToken(token)
    }

    override suspend fun isUserAuthorized(): Boolean {
        return !authPreferenceRepository.getToken().isNullOrEmpty()
    }

    override suspend fun registerNewUser(registerRequestData: RegisterRequestData) {
        val userToken = authRepository.registerNewUser(registerRequestData.registerUserInfo)
        if (!userToken.isNullOrEmpty()) {
            registerRequestData.userInfo.singUpDate = dateFormat.format(Calendar.getInstance().time)
            userDBRepository.addUser(
                authRepository.getUserId()!!,
                registerRequestData.userInfo
            )
        }
        authPreferenceRepository.saveToken(userToken)
    }

    override suspend fun uploadUserProfilePhoto(uri: Uri?, bitmap: Bitmap?) {
        storageRepository.uploadProfilePhoto(authRepository.getUserId()!!, uri, bitmap)
    }

    override suspend fun updateEnglishLevel(levelValue: String) {
        userDBRepository.updateUser(authRepository.getUserId()!!, levelValue)
    }

    override suspend fun getUserId(): String? {
        return authRepository.getUserId()
    }

    override suspend fun logout() {
        authRepository.logout()
        authPreferenceRepository.saveToken(null)
    }

    override suspend fun deleteAccount(): String? {
        authRepository.deleteUser()?.let {
            userDBRepository.deleteUserInfo(it)
            storageRepository.removeProfilePhoto(it)
            authPreferenceRepository.saveToken(null)
            return it
        }
        return null
    }

    override suspend fun isUsernameAvailable(username: String): Boolean {
        return userDBRepository.isUsernameAvailable(username)
    }

    override suspend fun getUserInfo(wordsCount: Int): UserProfileInfoUIModel {
        val userId = authRepository.getUserId()
        val photo = storageRepository.getProfilePhoto(userId!!)
        return userDBRepository.getUserInfo(userId)!!.toUI(resources, wordsCount.toString(), photo)
    }
}
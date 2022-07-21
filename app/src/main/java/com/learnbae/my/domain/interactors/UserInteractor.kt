package com.learnbae.my.domain.interactors

import android.annotation.SuppressLint
import android.content.res.Resources
import android.graphics.Bitmap
import android.net.Uri
import com.learnbae.my.data.storage.entities.PasswordChangeModel
import com.learnbae.my.data.storage.entities.RegisterRequestData
import com.learnbae.my.data.storage.entities.UpdateUserEntity
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
    private val userId = getUserId()

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
                getUserId()!!,
                registerRequestData.userInfo
            )
        }
        authPreferenceRepository.saveToken(userToken)
    }

    override suspend fun uploadUserProfilePhoto(uri: Uri) {
        storageRepository.uploadProfilePhoto(userId!!, uri)
    }

    override suspend fun updateEnglishLevel(levelValue: String) {
        userDBRepository.updateUser(userId!!, levelValue)
    }

    override fun getUserId(): String? {
        return authRepository.getUserId()
    }

    override suspend fun isCodeValid(code: String): Boolean {
        return authRepository.isCodeValid(code)
    }

    override suspend fun changeUserPassword(passwordChangeModel: PasswordChangeModel) {
        authRepository.updateUserPassword(
            passwordChangeModel.currentPassword,
            passwordChangeModel.newPassword
        )
    }

    override suspend fun updateUserInfo(updateUserEntity: UpdateUserEntity) {
        updateUserEntity.apply {
            profilePhoto?.let { uploadUserProfilePhoto(Uri.parse(it)) }
            email?.let { authRepository.updateUserEmail(it) }
        }

        userDBRepository.updateUserProfileInformation(userId!!, updateUserEntity)
    }

    override suspend fun resetPassword(code: String, newPassword: String) {
        authRepository.resetPassword(code, newPassword)
    }

    override suspend fun sendEmailResetPasswordMessage(email: String) {
        authRepository.sendEmailResetPasswordMessage(email)
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
        return userDBRepository.getUserInfo(userId)!!.toUI(resources, wordsCount.toString(), photo.toString())
    }
}
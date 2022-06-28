package com.learnbae.my.domain.interactors

import android.annotation.SuppressLint
import com.learnbae.my.data.storage.entities.RegisterRequestData
import com.learnbae.my.domain.datacontracts.interfaces.IAuthRepository
import com.learnbae.my.domain.datacontracts.interfaces.IAuthorizationStorageRepository
import com.learnbae.my.domain.datacontracts.interfaces.IUserDBRepository
import com.learnbae.my.domain.interfaces.IUserInteractor
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
class UserInteractor(
    private val authRepository: IAuthRepository,
    private val authPreferenceRepository: IAuthorizationStorageRepository,
    private val userDBRepository: IUserDBRepository
) : IUserInteractor {

    private val dateFormat = SimpleDateFormat("yyyy-MM-dd")
    override suspend fun loginByEmailAndPassword(email: String, password: String) {
        val token = authRepository.loginByEmailAndPassword(email, password)
        authPreferenceRepository.saveToken(token)
    }

    override suspend fun isUserAuthorized(): Boolean {
        return !authPreferenceRepository.getToken().isNullOrEmpty()
    }

    override suspend fun registerNewUser(registerRequestData: RegisterRequestData) {
        val isCreated = authRepository.registerNewUser(registerRequestData.registerUserInfo)
        if (isCreated) {
            registerRequestData.userInfo.singUpDate = dateFormat.format(Calendar.getInstance().time)
            userDBRepository.addUser(registerRequestData.userInfo)
        }
    }

    override suspend fun logout() {
        authRepository.logout()
        authPreferenceRepository.saveToken(null)
    }
}
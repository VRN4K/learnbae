package com.learnbae.my.domain.interfaces

import com.learnbae.my.data.storage.entities.RegisterRequestData

interface IUserInteractor {
    suspend fun loginByEmailAndPassword(email: String, password: String)
    suspend fun isUserAuthorized(): Boolean
    suspend fun logout()
    suspend fun registerNewUser(registerRequestData: RegisterRequestData)
}
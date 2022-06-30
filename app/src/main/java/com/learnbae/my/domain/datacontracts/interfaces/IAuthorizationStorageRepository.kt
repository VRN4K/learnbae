package com.learnbae.my.domain.datacontracts.interfaces

interface IAuthorizationStorageRepository {
    suspend fun saveToken(token: String?)
    suspend fun getToken(): String?
}
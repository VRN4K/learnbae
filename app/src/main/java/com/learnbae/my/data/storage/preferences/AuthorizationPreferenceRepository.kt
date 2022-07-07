package com.learnbae.my.data.storage.preferences

import com.learnbae.my.domain.datacontracts.interfaces.IAuthorizationStorageRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthorizationPreferenceRepository @Inject constructor(
    private val tokenPreference: TokenPreference,
) : IAuthorizationStorageRepository {
    override suspend fun saveToken(token: String?) {
        tokenPreference.set(token)
    }

    override suspend fun getToken() = tokenPreference.get()
}
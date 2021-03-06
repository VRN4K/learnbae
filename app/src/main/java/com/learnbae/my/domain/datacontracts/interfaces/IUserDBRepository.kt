package com.learnbae.my.domain.datacontracts.interfaces

import com.learnbae.my.data.storage.entities.UpdateUserEntity
import com.learnbae.my.data.storage.entities.UserEntity

interface IUserDBRepository {
    suspend fun addUser(userId: String, userInfo: UserEntity)
    suspend fun getUserInfo(userId: String): UserEntity?
    suspend fun isUsernameAvailable(username: String): Boolean
    fun updateUser(userId: String, levelValue: String)
    fun updateUserProfileInformation(userId: String, userInfo: UpdateUserEntity)
    fun deleteUserInfo(userId: String)
}
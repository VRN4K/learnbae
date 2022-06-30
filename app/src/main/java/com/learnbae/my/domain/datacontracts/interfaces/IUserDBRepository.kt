package com.learnbae.my.domain.datacontracts.interfaces

import com.learnbae.my.data.storage.entities.UserEntity

interface IUserDBRepository {
    suspend fun addUser(userId: String, userInfo: UserEntity)
    suspend fun getUserInfo(userId: String): UserEntity?
}
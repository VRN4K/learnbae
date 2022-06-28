package com.learnbae.my.domain.datacontracts.interfaces

import com.learnbae.my.data.storage.entities.RegisterUserInfo
import com.learnbae.my.data.storage.entities.UserEntity

interface IUserDBRepository {
    suspend fun addUser(userInfo: UserEntity)
}
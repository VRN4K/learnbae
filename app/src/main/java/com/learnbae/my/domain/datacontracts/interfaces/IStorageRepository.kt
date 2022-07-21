package com.learnbae.my.domain.datacontracts.interfaces

import android.net.Uri

interface IStorageRepository {
    fun uploadProfilePhoto(userId: String, uri: Uri)
    suspend fun removeProfilePhoto(userId: String)
    suspend fun getProfilePhoto(userId: String): Uri?
}
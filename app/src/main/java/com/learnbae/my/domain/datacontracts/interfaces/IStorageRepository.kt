package com.learnbae.my.domain.datacontracts.interfaces

import android.graphics.Bitmap
import android.net.Uri

interface IStorageRepository {
    fun uploadProfilePhoto(userId: String, photo: Bitmap)
    suspend fun removeProfilePhoto(userId: String)
    suspend fun getProfilePhoto(userId: String): Uri?
}
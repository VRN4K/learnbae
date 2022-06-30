package com.learnbae.my.data.net.repository

import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import com.google.firebase.storage.FirebaseStorage
import com.learnbae.my.domain.datacontracts.interfaces.IStorageRepository
import java.io.ByteArrayOutputStream
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class FirebaseStorageRepository(private val storage: FirebaseStorage) : IStorageRepository {
    override fun uploadProfilePhoto(userId: String, uri: Uri?, bitmap: Bitmap?) {
        with(storage.reference.child("images").child(userId).child("profile.jpg")) {
            bitmap?.let {
                putBytes(with(ByteArrayOutputStream()) {
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, this)
                    this.toByteArray()
                })
            } ?: uri?.let { putFile(it) }
        }?.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d("Profile", "uploadPhoto:success")
            } else {
                Log.d("Profile", "uploadPhoto:failure", task.exception)
            }
        }
    }

    override suspend fun getProfilePhoto(userId: String): Uri? {
        return suspendCoroutine {
            storage.reference.child("images/$userId/profile.jpg").downloadUrl.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("Profile", "downloadPhoto:success")
                    it.resume(task.result)
                } else {
                    Log.d("Profile", "downloadPhoto:failure", task.exception)
                    it.resume(null)
                }
            }
        }
    }
}
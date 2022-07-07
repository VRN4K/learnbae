package com.learnbae.my.data.net.repository

import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import com.google.firebase.storage.FirebaseStorage
import com.learnbae.my.domain.datacontracts.interfaces.IStorageRepository
import java.io.ByteArrayOutputStream
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@Singleton
class FirebaseStorageRepository @Inject constructor(private val storage: FirebaseStorage) :
    IStorageRepository {

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

    override suspend fun removeProfilePhoto(userId: String) {
        getProfilePhoto(userId)?.let {
            storage.reference.child("images/$userId").delete().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("Profile", "removePhoto:success")
                } else {
                    Log.d("Profile", "removePhoto:failure", task.exception)
                }
            }
        }
    }

    override suspend fun getProfilePhoto(userId: String): Uri? {
        return suspendCoroutine {
            storage.reference.child("images/$userId").list(1).addOnCompleteListener { task ->
                if (task.result.items.size > 0) {
                    Log.d("Profile", "downloadPhoto:success")
                    storage.reference.child("images/$userId/profile.jpg").downloadUrl.addOnCompleteListener { task ->
                        it.resume(task.result)
                    }
                } else {
                    Log.d("Profile", "downloadPhoto:failure")
                    it.resume(null)
                }
            }
        }
    }
}
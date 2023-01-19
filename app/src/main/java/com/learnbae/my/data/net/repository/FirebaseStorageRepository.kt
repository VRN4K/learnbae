package com.learnbae.my.data.net.repository

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.core.graphics.drawable.toBitmap
import com.bumptech.glide.Glide
import com.google.firebase.storage.FirebaseStorage
import com.learnbae.my.domain.datacontracts.interfaces.IStorageRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.ByteArrayOutputStream
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@Singleton
class FirebaseStorageRepository @Inject constructor(
    private val storage: FirebaseStorage,
    @ApplicationContext val context: Context
) :
    IStorageRepository {
    override fun uploadProfilePhoto(userId: String, photo: Bitmap) {
        val baos = ByteArrayOutputStream()
        with(storage.reference.child("images").child(userId).child("profile.jpg")) {
            photo.compress(Bitmap.CompressFormat.PNG, 100, baos)
            putBytes(baos.toByteArray())
        }.addOnCompleteListener { task ->
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
                        Log.d("Profile", "Получение файла фото")
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
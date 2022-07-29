package com.learnbae.my.data.storage.repositories

import android.util.Log
import com.google.firebase.database.FirebaseDatabase
import com.google.gson.Gson
import com.learnbae.my.data.net.model.UserInfoModel
import com.learnbae.my.data.storage.entities.UpdateUserEntity
import com.learnbae.my.data.storage.entities.UserEntity
import com.learnbae.my.domain.datacontracts.interfaces.IUserDBRepository
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@Singleton
class UserDBRepository @Inject constructor(private val database: FirebaseDatabase) :
    IUserDBRepository {
    companion object {
        private const val DB_USER_REF_NAME = "USER"
    }
    private val dataBaseReference by lazy { database.getReference(DB_USER_REF_NAME) }

    override suspend fun addUser(userId: String, userInfo: UserEntity) {
        dataBaseReference.child(userId).setValue(userInfo).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d("USER", "addUserInfo:success")
            } else {
                Log.d("USER", "addUserInfo:failure", task.exception)
            }
        }
    }

    override suspend fun getUserInfo(userId: String): UserEntity? {
        val userInfo = mutableMapOf<String, String>()
        return suspendCoroutine {
            dataBaseReference.child(userId).get().addOnSuccessListener { dataSnapshot ->
                dataSnapshot.children.onEach { userData ->
                    userInfo[userData.key!!] = userData.value.toString()
                }
                it.resume(
                    UserEntity(
                        userInfo["username"]!!,
                        userInfo["userFullName"]!!,
                        userInfo["englishLevel"]!!,
                        userInfo["email"]!!,
                        userInfo["singUpDate"]!!
                    )
                )
            }.addOnFailureListener {
                Log.d("USER", "Error getting data", it)
            }
        }
    }

    override suspend fun isUsernameAvailable(username: String): Boolean {
        return suspendCoroutine { continuation ->
            dataBaseReference.get().addOnCompleteListener { task ->
                val usersInfo = (task.result.value as Map<*, *>).map { Gson().toJson(it.value) }
                    .map { Gson().fromJson(it, UserInfoModel::class.java) }
                usersInfo.find { it.username == username }?.let {
                    Log.d("USER", "checkUsername:already exists")
                    continuation.resume(false)
                } ?: continuation.resume(true).also { Log.d("USER", "checkUsername:free") }
            }
        }
    }

    override fun updateUser(userId: String, levelValue: String) {
        dataBaseReference.child(userId).child("englishLevel").setValue(levelValue)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("USER", "addUserInfo:success")
                } else {
                    Log.d("USER", "addUserInfo:failure", task.exception)
                }
            }
    }

    override fun updateUserProfileInformation(userId: String, userInfo: UpdateUserEntity) {
        dataBaseReference.child(userId).apply {
            userInfo.userFullName?.let { child("userFullName").setValue(it) }
            userInfo.username?.let { child("username").setValue(it) }
            userInfo.email?.let { child("email").setValue(it) }
        }
    }

    override fun deleteUserInfo(userId: String) {
        dataBaseReference.child(userId).removeValue().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d("USER", "removeUserInfo:success")
            } else {
                Log.d("USER", "removeUser:failure", task.exception)
            }
        }
    }
}
package com.learnbae.my.data.storage.repositories

import android.util.Log
import com.google.firebase.database.FirebaseDatabase
import com.learnbae.my.data.storage.entities.UserEntity
import com.learnbae.my.domain.datacontracts.interfaces.IUserDBRepository
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class UserDBRepository(private val database: FirebaseDatabase) : IUserDBRepository {
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
                task.result.children.find { it.key == "username" && it.value == username }
                    ?.let {
                        continuation.resume(true).also {
                            println(
                                "answer: $it"
                            )
                        }
                    } ?: continuation.resume(false).also {
                    println(
                        "answer: $it"
                    )
                }
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
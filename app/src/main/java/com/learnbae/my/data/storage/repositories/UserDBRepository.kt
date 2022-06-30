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
                Log.d("Reg", "addUserInfo:success")
            } else {
                Log.d("Reg", "addUserInfo:success:failure", task.exception)
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
                Log.d("Login", "Error getting data", it)
            }
        }
    }
}
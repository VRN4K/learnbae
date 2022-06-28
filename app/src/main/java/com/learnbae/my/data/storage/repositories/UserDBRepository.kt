package com.learnbae.my.data.storage.repositories

import android.util.Log
import com.google.firebase.database.FirebaseDatabase
import com.learnbae.my.data.storage.entities.UserEntity
import com.learnbae.my.domain.datacontracts.interfaces.IUserDBRepository

class UserDBRepository(private val database: FirebaseDatabase) : IUserDBRepository {
    companion object {
        private const val DB_USER_REF_NAME = "USER"
    }

    private val dataBaseReference by lazy { database.getReference(DB_USER_REF_NAME) }

    override suspend fun addUser(userInfo: UserEntity) {
        dataBaseReference.push().setValue(userInfo).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d("Reg", "addUserInfo:success")
            } else {
                Log.d("Reg", "addUserInfo:success:failure", task.exception)
            }
        }
    }
}
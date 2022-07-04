package com.learnbae.my.data.net.repository

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.learnbae.my.data.storage.entities.RegisterUserInfo
import com.learnbae.my.domain.datacontracts.interfaces.IAuthRepository
import com.learnbae.my.presentation.common.exceptions.WrongEmailOrPasswordException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class AuthRepository(private val firebaseAuth: FirebaseAuth) :
    IAuthRepository {
    override suspend fun loginByEmailAndPassword(email: String, password: String): String? {
        return suspendCoroutine {
            firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d("Login", "signInWithEmail:success")
                        Log.d("Login", "user: ${firebaseAuth.currentUser!!.email}")
                        firebaseAuth.currentUser?.getIdToken(true)
                            ?.addOnCompleteListener { taskToken ->
                                it.resume(taskToken.result.token)
                            }
                    } else {
                        Log.d("Login", "signInWithEmail:failure")
                        if (task.exception is FirebaseAuthInvalidUserException ||
                            task.exception is FirebaseAuthInvalidCredentialsException
                        ) {
                            it.resumeWithException(WrongEmailOrPasswordException())
                        }
                    }
                }
        }
    }

    override suspend fun registerNewUser(user: RegisterUserInfo): String? {
        return suspendCoroutine {
            firebaseAuth.createUserWithEmailAndPassword(user.email, user.password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d("Reg", "signUpWithEmail:success")
                        Log.d("Reg", "user: ${firebaseAuth.currentUser!!.email}")
                    } else {
                        Log.d("Reg", "signUpWithEmail:failure", task.exception)
                    }
                    firebaseAuth.currentUser?.getIdToken(true)?.addOnCompleteListener { taskToken ->
                        it.resume(taskToken.result.token)
                    }
                }
        }
    }

    override suspend fun getUserId(): String? {
        return firebaseAuth.currentUser?.uid
    }

    override suspend fun logout() {
        firebaseAuth.signOut()
    }
}
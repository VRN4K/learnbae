package com.learnbae.my.data.net.repository

import android.util.Log
import com.google.firebase.auth.*
import com.learnbae.my.data.storage.entities.RegisterUserInfo
import com.learnbae.my.domain.datacontracts.interfaces.IAuthRepository
import com.learnbae.my.presentation.common.exceptions.UsernameOrEmailAlreadyExistException
import com.learnbae.my.presentation.common.exceptions.WrongEmailOrPasswordException
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

@Singleton
class AuthRepository @Inject constructor(private val firebaseAuth: FirebaseAuth) :
    IAuthRepository {
    override suspend fun loginByEmailAndPassword(email: String, password: String): String? {
        return suspendCoroutine {
            firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d("Auth", "signInWithEmail:success")
                        Log.d("Auth", "user: ${firebaseAuth.currentUser!!.email}")
                        firebaseAuth.currentUser?.getIdToken(true)
                            ?.addOnCompleteListener { taskToken ->
                                it.resume(taskToken.result.token)
                            }
                    } else {
                        Log.d("Auth", "signInWithEmail:failure")
                        if (task.exception is FirebaseAuthInvalidUserException ||
                            task.exception is FirebaseAuthInvalidCredentialsException
                        ) {
                            it.resumeWithException(WrongEmailOrPasswordException())
                        }
                    }
                }
        }
    }

    override fun updateUserPassword(currentPassword: String, newPassword: String) {
        firebaseAuth.currentUser!!.reauthenticate(
            EmailAuthProvider.getCredential(
                firebaseAuth.currentUser!!.email!!,
                currentPassword
            )
        ).addOnCompleteListener {
            Log.d("Auth", "changePassword: user re-authenticate")
        }

        firebaseAuth.currentUser!!.updatePassword(newPassword).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d("Auth", "changePassword:success")
                Log.d("Auth", "user: ${firebaseAuth.currentUser!!.email}")
            } else {
                Log.d("Auth", "changePassword:failure", task.exception)
            }
        }
    }

    override fun sendResetPasswordCode() {
        firebaseAuth.currentUser
    }


    override suspend fun registerNewUser(user: RegisterUserInfo): String? {
        return suspendCoroutine {
            firebaseAuth.createUserWithEmailAndPassword(user.email, user.password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d("Auth", "signUpWithEmail:success")
                        Log.d("Auth", "user: ${firebaseAuth.currentUser!!.email}")
                    } else {
                        Log.d("Auth", "signUpWithEmail:failure", task.exception)

                        if (task.exception is FirebaseAuthUserCollisionException) {
                            it.resumeWithException(UsernameOrEmailAlreadyExistException())
                        }
                    }
                    firebaseAuth.currentUser?.getIdToken(true)?.addOnCompleteListener { taskToken ->
                        it.resume(taskToken.result.token)
                    }
                }
        }
    }

    override fun getUserId(): String? {
        return firebaseAuth.currentUser?.uid
    }

    override suspend fun logout() {
        firebaseAuth.signOut()
    }

    override suspend fun deleteUser(): String? {
        val userId = getUserId()
        return suspendCoroutine {
            firebaseAuth.currentUser!!.delete().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("Auth", "userDeleting:success")
                    it.resume(userId)
                } else {
                    Log.d("Auth", "userDeleting:failure", task.exception)
                    it.resume(null)
                }
            }
        }
    }
}
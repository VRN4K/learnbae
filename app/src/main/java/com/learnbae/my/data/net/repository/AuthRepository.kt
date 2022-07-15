package com.learnbae.my.data.net.repository

import android.util.Log
import com.google.firebase.auth.*
import com.learnbae.my.data.storage.entities.RegisterUserInfo
import com.learnbae.my.domain.datacontracts.interfaces.IAuthRepository
import com.learnbae.my.presentation.common.exceptions.UsernameOrEmailAlreadyExistException
import com.learnbae.my.presentation.common.exceptions.WrongCurrentPasswordException
import com.learnbae.my.presentation.common.exceptions.WrongEmailOrPasswordException
import com.learnbae.my.presentation.common.exceptions.createExceptionHandler
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

    override suspend fun updateUserPassword(currentPassword: String, newPassword: String) {
        suspendCoroutine<Unit> {
            firebaseAuth.currentUser!!.reauthenticate(
                EmailAuthProvider.getCredential(
                    firebaseAuth.currentUser!!.email!!,
                    currentPassword
                )
            ).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("Auth", "changePassword: user re-authenticate")
                } else {
                    Log.d("Auth", "changePassword: user re-authentication failed")
                    it.resumeWithException(WrongCurrentPasswordException())
                }
            }

            firebaseAuth.currentUser!!.updatePassword(newPassword).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("Auth", "changePassword:success")
                    Log.d("Auth", "user: ${firebaseAuth.currentUser!!.email}")
                    it.resume(Unit)
                } else {
                    Log.d("Auth", "changePassword:failure", task.exception)
                }
            }
        }
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

    override suspend fun resetPassword(code: String, newPassword: String) {
        firebaseAuth.confirmPasswordReset(code, newPassword).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d("Auth", "resetPassword:success")
            } else {
                Log.d("Auth", "resetPassword:failure", task.exception)
            }
        }
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
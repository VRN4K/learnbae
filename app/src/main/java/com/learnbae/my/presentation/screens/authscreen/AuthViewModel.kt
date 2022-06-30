package com.learnbae.my.presentation.screens.authscreen

import android.util.Patterns
import androidx.lifecycle.MutableLiveData
import com.learnbae.my.R
import com.learnbae.my.domain.interfaces.IUserInteractor
import com.learnbae.my.presentation.base.BaseViewModel
import com.learnbae.my.presentation.common.exceptions.createExceptionHandler
import com.learnbae.my.presentation.screens.Screens
import ltst.nibirualert.my.domain.launchIO
import org.koin.core.component.inject

class AuthViewModel : BaseViewModel() {
    private val userInteractor: IUserInteractor by inject()
    val userError = MutableLiveData<Int?>()
    val emailError = MutableLiveData<Int?>()
    val passwordError = MutableLiveData<Int?>()

    fun singInByEmailAndPassword(email: String, password: String) {
        val emailValidationResult = email.getValidationEmailResult()
        val passwordValidationResult = password.getValidationPasswordResult()

        if (emailValidationResult && passwordValidationResult) {
            launchIO(createExceptionHandler {
                onException(it)
                println("Loh")
//                if (it is FirebaseAuthInvalidUserException){
//                    userError.postValue(R.string.user_error_text)
//                }
//                if (it is FirebaseAuthException){
//                    userError.postValue(R.string.user_wrong_password_or_email)
//                }
            }
            ) {
                userInteractor.loginByEmailAndPassword(email, password)
                openFragment(Screens.getProfileScreen())
            }
        }
    }

    private fun String.getValidationEmailResult(): Boolean {
        var isValid = true
        when {
            this.isEmpty() -> emailError.postValue(R.string.empty_input_error_text)
                .also { isValid = false }
            !Patterns.EMAIL_ADDRESS.matcher(this)
                .matches() -> emailError.postValue(R.string.email_not_match_mask_text)
                .also { isValid = false }
            else -> emailError.postValue(null)
        }
        return isValid
    }

    private fun String.getValidationPasswordResult(): Boolean {
        var isValid = true
        when {
            this.isEmpty() -> passwordError.postValue(R.string.empty_input_error_text)
                .also { isValid = false }
            else -> passwordError.postValue(null)
        }
        return isValid
    }
}
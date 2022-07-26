package com.learnbae.my.presentation.screens.authscreen

import android.util.Patterns
import androidx.lifecycle.MutableLiveData
import com.learnbae.my.R
import com.learnbae.my.domain.interfaces.IUserInteractor
import com.learnbae.my.presentation.base.BaseViewModel
import com.learnbae.my.presentation.common.exceptions.WrongEmailOrPasswordException
import com.learnbae.my.presentation.common.exceptions.createExceptionHandler
import com.learnbae.my.presentation.screens.Screens
import dagger.hilt.android.lifecycle.HiltViewModel
import ltst.nibirualert.my.domain.launchIO
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor() : BaseViewModel() {
    @Inject
    lateinit var userInteractor: IUserInteractor

    val userError = MutableLiveData<Int?>()
    val emailError = MutableLiveData<Int?>()
    val passwordError = MutableLiveData<Int?>()
    val showButtonLoadingStatus = MutableLiveData<Boolean>()

    fun singInByEmailAndPassword(email: String, password: String) {
        showButtonLoadingStatus.postValue(true)
        val emailValidationResult = email.getValidationEmailResult()
        val passwordValidationResult = password.getValidationPasswordResult()

        if (emailValidationResult && passwordValidationResult) {
            launchIO(createExceptionHandler {
                onException(it)
                if (it is WrongEmailOrPasswordException) {
                    userError.postValue(R.string.user_wrong_password_or_email)
                    showButtonLoadingStatus.postValue(false)
                }
            }) {
                userError.postValue(null)
                userInteractor.loginByEmailAndPassword(email, password)
                openFragment(Screens.getProfileScreen())
            }
        } else {
            showButtonLoadingStatus.postValue(false)
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
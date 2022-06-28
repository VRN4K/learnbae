package com.learnbae.my.presentation.screens.registrationscreen

import android.annotation.SuppressLint
import android.util.Patterns
import androidx.lifecycle.MutableLiveData
import com.learnbae.my.R
import com.learnbae.my.data.storage.entities.RegisterRequestData
import com.learnbae.my.domain.interfaces.IUserInteractor
import com.learnbae.my.presentation.base.BaseViewModel
import ltst.nibirualert.my.domain.launchIO
import org.koin.core.component.inject

class RegistrationViewModel : BaseViewModel() {
    companion object {
        private const val PASSWORD_LENGTH = 5
    }

    private val userInteractor: IUserInteractor by inject()


    val userError = MutableLiveData<Int?>()
    val emailError = MutableLiveData<Int?>()
    val passwordError = MutableLiveData<Int?>()
    val usernameError = MutableLiveData<Int?>()
    val fullNameError = MutableLiveData<Int?>()


    fun registerNewUser(registerRequestData: RegisterRequestData) {
        val validationResult = mutableListOf(
            registerRequestData.userInfo.email.getValidationEmailResult(),
            registerRequestData.registerUserInfo.password.getValidationPasswordResult(),
            registerRequestData.userInfo.username.getValidationUsernameResult(),
            registerRequestData.userInfo.userFullName.getValidationFullNameResult(),
        )

        if (validationResult.all { true }) {
            launchIO { userInteractor.registerNewUser(registerRequestData) }
        }
    }

    private fun String.getValidationFullNameResult(): Boolean {
        var isValid = true
        when {
            this.isEmpty() -> fullNameError.postValue(R.string.empty_input_error_text)
                .also { isValid = false }
            !this.any { (it in 'а'..'я' || it in 'А'..'Я') || (it == '-' || it == ' ') }
            -> fullNameError.postValue(
                R.string.fullname_error_text
            ).also { isValid = false }
            else -> fullNameError.postValue(null)
        }
        return isValid
    }

    private fun String.getValidationUsernameResult(): Boolean {
        var isValid = true
        when {
            this.isEmpty() -> usernameError.postValue(R.string.empty_input_error_text)
                .also { isValid = false }
            !this.any { (it in 'a'..'z' || it in 'A'..'Z') || it in '0'..'9' || it == '_' }
            -> usernameError.postValue(R.string.username_error_text).also { isValid = false }
            else -> usernameError.postValue(null)
        }
        return isValid
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
            !this.any { (it in 'a'..'z' || it in 'A'..'Z') || it in '0'..'9' } || this.length < PASSWORD_LENGTH -> passwordError.postValue(
                R.string.password_text_error
            )
                .also { isValid = false }
            else -> passwordError.postValue(null)
        }
        return isValid
    }
}
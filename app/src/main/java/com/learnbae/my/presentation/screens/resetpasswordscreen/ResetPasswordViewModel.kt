package com.learnbae.my.presentation.screens.resetpasswordscreen

import android.util.Patterns
import com.learnbae.my.R
import com.learnbae.my.domain.interfaces.IUserInteractor
import com.learnbae.my.presentation.base.BaseViewModel
import com.learnbae.my.presentation.common.exceptions.createExceptionHandler
import com.learnbae.my.presentation.common.livedata.SingleLiveEvent
import com.learnbae.my.presentation.screens.Screens
import dagger.hilt.android.lifecycle.HiltViewModel
import ltst.nibirualert.my.domain.launchIO
import javax.inject.Inject

@HiltViewModel
class ResetPasswordViewModel @Inject constructor(
    private val userInteractor: IUserInteractor
) : BaseViewModel() {

    val emailError = SingleLiveEvent<Int?>()
    val passwordError = SingleLiveEvent<Int?>()
    val dialogMessage = SingleLiveEvent<Int?>()

    fun resetPassword(code: String, newPassword: String) {
        launchIO(createExceptionHandler {
            onException(it)
        }) {
            val isValid = newPassword.getValidationPasswordResult()

            if (isValid) {
                userInteractor.resetPassword(code, newPassword)
                passwordError.postValue(null)
                navigateToScreen(Screens.getMainScreen())
                dialogMessage.postValue(R.string.reset_password_change_password_changed)
            }
        }
    }

    fun sendEmailPasswordResetMessage(email: String) {
        launchIO(createExceptionHandler {
            onException(it)
            emailError.postValue(R.string.user_error_text)
        }) {
            val isValid = email.getValidationEmailResult()

            if (isValid) {
                userInteractor.sendEmailResetPasswordMessage(email)
                emailError.postValue(null)
                navigateToPreviousScreen()
                dialogMessage.postValue(R.string.reset_password_change_email_send)
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
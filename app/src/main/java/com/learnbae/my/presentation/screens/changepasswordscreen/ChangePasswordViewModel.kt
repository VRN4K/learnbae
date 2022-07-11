package com.learnbae.my.presentation.screens.changepasswordscreen

import androidx.lifecycle.MutableLiveData
import com.learnbae.my.R
import com.learnbae.my.data.storage.entities.PasswordChangeModel
import com.learnbae.my.domain.interfaces.IUserInteractor
import com.learnbae.my.presentation.base.BaseViewModel
import com.learnbae.my.presentation.common.exceptions.WrongCurrentPasswordException
import com.learnbae.my.presentation.common.exceptions.createExceptionHandler
import com.learnbae.my.presentation.screens.registrationscreen.RegistrationViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ltst.nibirualert.my.domain.launchIO
import javax.inject.Inject

@HiltViewModel
class ChangePasswordViewModel @Inject constructor(
    val userInteractor: IUserInteractor
) : BaseViewModel() {
    companion object {
        private const val PASSWORD_LENGTH = 6
    }

    val currentPasswordError = MutableLiveData<Int?>()
    val newPasswordError = MutableLiveData<Int?>()

    fun changePassword(passwordChangeModel: PasswordChangeModel) {
        val currentPasswordErrorText =
            passwordChangeModel.currentPassword.getValidationPasswordResult().also {
                currentPasswordError.postValue(it)
            }

        val newPasswordErrorText =
            passwordChangeModel.newPassword.getValidationPasswordResult().also {
                newPasswordError.postValue(it)
            }

        if (currentPasswordErrorText == null && newPasswordErrorText == null) {
            launchIO(createExceptionHandler {
                onException(it)
                if (it is WrongCurrentPasswordException) currentPasswordError.postValue(R.string.change_password_wrong_current_password_error)
            }) {
                userInteractor.changeUserPassword(passwordChangeModel)
                navigateToPreviousScreen()
            }
        }
    }

    private fun String.getValidationPasswordResult(): Int? {
        return when {
            this.isEmpty() -> R.string.empty_input_error_text
            !this.any { (it in 'a'..'z' || it in 'A'..'Z') || it in '0'..'9' } || this.length < PASSWORD_LENGTH -> R.string.password_text_error
            else -> null
        }
    }
}
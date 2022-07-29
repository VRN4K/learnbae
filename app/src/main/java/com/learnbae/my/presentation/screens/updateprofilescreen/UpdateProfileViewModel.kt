package com.learnbae.my.presentation.screens.updateprofilescreen

import android.graphics.Bitmap
import android.util.Patterns
import androidx.lifecycle.MutableLiveData
import com.learnbae.my.R
import com.learnbae.my.data.storage.entities.UpdateUserEntity
import com.learnbae.my.domain.datacontracts.model.UserProfileInfoUIModel
import com.learnbae.my.domain.datacontracts.model.UserUpdateInformationUI
import com.learnbae.my.domain.interfaces.IUserInteractor
import com.learnbae.my.presentation.base.BaseViewModel
import com.learnbae.my.presentation.common.exceptions.createExceptionHandler
import com.learnbae.my.presentation.screens.Screens
import com.learnbae.my.presentation.screens.registrationscreen.RegistrationViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ltst.nibirualert.my.domain.launchIO
import javax.inject.Inject

@HiltViewModel
class UpdateProfileViewModel @Inject constructor(
    private val userInteractor: IUserInteractor
) : BaseViewModel() {
    private val currentUserInformation: UserProfileInfoUIModel = userInteractor.getCurrentUser()
    private var userPhoto: Bitmap? = null

    val usernameError = MutableLiveData<Int?>()
    val passwordHelperText = MutableLiveData<Int?>()
    val successDialogText = MutableLiveData<Int?>()
    val emailError = MutableLiveData<Int?>()
    val passwordError = MutableLiveData<Int?>()
    val fullNameError = MutableLiveData<Int?>()
    val userData = MutableLiveData<UserProfileInfoUIModel>()

    init {
        userData.postValue(currentUserInformation)
    }

    fun onChangePhotoButtonClick(photo: Bitmap) {
        userPhoto = photo
    }

    fun saveChanges(updateUserEntity: UpdateUserEntity) {
        launchIO(createExceptionHandler {
            onException(it)
        }) {

            userPhoto?.let {
                updateUserEntity.profilePhoto = it
            }

            val validationResult = mutableListOf(
                updateUserEntity.email?.getValidationEmailResult() ?: false,
                updateUserEntity.username?.getValidationUsernameResult() ?: false,
                updateUserEntity.userFullName?.getValidationFullNameResult() ?: false,
            )

            if (!validationResult.contains(false)) {
                updateUserEntity.isDataChanged()
                updateUserEntity.username?.let {
                    if (userInteractor.isUsernameAvailable(it)) {
                        updateUserEntity.email?.let {
                            updateUserEntity.currentPassword?.let { password ->
                                if (password.getValidationPasswordResult()) {
                                    updateUserInfo(updateUserEntity)
                                }
                            } ?: passwordError.postValue(R.string.empty_input_error_text)
                        }
                        updateUserInfo(updateUserEntity)
                    } else {
                        usernameError.postValue(R.string.username_already_exist_error_text)
                    }
                } ?: updateUserEntity.email?.let {
                    updateUserEntity.currentPassword?.let { password ->
                        val isPasswordValid = password.getValidationPasswordResult()
                        if (isPasswordValid) {
                            updateUserInfo(updateUserEntity)
                        }
                    } ?: passwordError.postValue(R.string.empty_input_error_text)
                } ?: updateUserInfo(updateUserEntity)
            }
        }
    }

    private suspend fun updateUserInfo(updateUserEntity: UpdateUserEntity) {
        userInteractor.updateUserInfo(updateUserEntity)
        navigateToPreviousScreen()
        successDialogText.postValue(R.string.update_screen_message_success)
    }

    fun onEmailEditTextFocus(isFocused: Boolean) {
        passwordHelperText.postValue(if (isFocused) R.string.update_screen_password_message else null)
    }

    fun logOut() {
        launchIO {
            userInteractor.logout()
            navigateToScreen(Screens.getAuthScreen())
        }
    }

    private fun UpdateUserEntity.isDataChanged(): UpdateUserEntity {
        username =
            if (username == currentUserInformation.username.substringAfter("@")) null else username
        email = if (email == currentUserInformation.email) null else email
        userFullName =
            if (userFullName == currentUserInformation.userFullName) null else userFullName
        return this
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
                .also { isValid = false }
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
            !this.any { (it in 'a'..'z' || it in 'A'..'Z') || it in '0'..'9' } || this.length < RegistrationViewModel.PASSWORD_LENGTH -> passwordError.postValue(
                R.string.password_text_error
            ).also { isValid = false }
            else -> passwordError.postValue(null)
        }
        return isValid
    }
}
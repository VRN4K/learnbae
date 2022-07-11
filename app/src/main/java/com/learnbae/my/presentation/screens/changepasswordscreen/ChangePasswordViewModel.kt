package com.learnbae.my.presentation.screens.changepasswordscreen

import com.learnbae.my.data.storage.entities.PasswordChangeModel
import com.learnbae.my.domain.interfaces.IUserInteractor
import com.learnbae.my.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ChangePasswordViewModel @Inject constructor(
    val userInteractor: IUserInteractor
) : BaseViewModel() {

    fun changePassword(passwordChangeModel: PasswordChangeModel) {
        userInteractor.changeUserPassword(passwordChangeModel)
        navigateToPreviousScreen()
    }
}
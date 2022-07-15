package com.learnbae.my.presentation.screens.resetpasswordscreen

import com.learnbae.my.domain.interfaces.IUserInteractor
import com.learnbae.my.presentation.base.BaseViewModel
import com.learnbae.my.presentation.screens.Screens
import dagger.hilt.android.lifecycle.HiltViewModel
import ltst.nibirualert.my.domain.launchIO
import javax.inject.Inject

@HiltViewModel
class ResetPasswordViewModel @Inject constructor(
    private val userInteractor: IUserInteractor
) : BaseViewModel() {

    fun resetPassword(code: String, newPassword: String) {
        launchIO {
            userInteractor.resetPassword(code, newPassword)
            navigateToScreen(Screens.getMainScreen())
        }
    }
}
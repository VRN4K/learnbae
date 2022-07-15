package com.learnbae.my.presentation.screens.updateprofilescreen

import androidx.lifecycle.MutableLiveData
import com.learnbae.my.R
import com.learnbae.my.data.storage.entities.UpdateUserEntity
import com.learnbae.my.domain.interfaces.IUserInteractor
import com.learnbae.my.presentation.base.BaseViewModel
import com.learnbae.my.presentation.screens.Screens
import dagger.hilt.android.lifecycle.HiltViewModel
import ltst.nibirualert.my.domain.launchIO
import javax.inject.Inject

@HiltViewModel
class UpdateProfileViewModel @Inject constructor(
    private val userInteractor: IUserInteractor
) : BaseViewModel() {
    val usernameError = MutableLiveData<Int?>()

    fun saveChanges(updateUserEntity: UpdateUserEntity) {
        launchIO {
            if (userInteractor.isUsernameAvailable(updateUserEntity.username!!)) {
                userInteractor.updateUserInfo(updateUserEntity)
                navigateToPreviousScreen()
            } else {
                usernameError.postValue(R.string.username_already_exist_error_text)
            }
        }
    }



    fun logOut() {
        launchIO {
            userInteractor.logout()
            navigateToScreen(Screens.getAuthScreen())
        }
    }
}
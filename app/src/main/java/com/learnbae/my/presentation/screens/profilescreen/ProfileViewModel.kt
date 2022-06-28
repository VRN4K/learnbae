package com.learnbae.my.presentation.screens.profilescreen

import com.learnbae.my.domain.datacontracts.model.UserProfileInfoUIModel
import com.learnbae.my.domain.interfaces.IUserInteractor
import com.learnbae.my.presentation.base.BaseViewModel
import com.learnbae.my.presentation.common.livedata.StateLiveData
import com.learnbae.my.presentation.screens.Screens
import ltst.nibirualert.my.domain.launchIO
import org.koin.core.component.inject

class ProfileViewModel : BaseViewModel() {
    private val userInteractor: IUserInteractor by inject()
    val userInformation = StateLiveData<UserProfileInfoUIModel>()

    init {
        userInformation.postLoading()
        launchIO {
            if (userInteractor.isUserAuthorized()) {
                //userInformation.postComplete(UserProfileInfoUIModel())
            } else {
                openFragment(Screens.getAuthScreen())
            }
        }
    }

    fun logout() {
        launchIO {
            userInteractor.logout()
            openFragment(Screens.getAuthScreen())
        }
    }
}
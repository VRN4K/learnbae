package com.learnbae.my.presentation.base.primaryfragment

import com.learnbae.my.presentation.base.BaseViewModel
import com.learnbae.my.presentation.screens.Screens

class PrimaryViewModel : BaseViewModel() {
    override fun openRootScreen() {
        router.newRootScreen(Screens.getMainScreen())
    }
}
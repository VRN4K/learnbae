package com.learnbae.my

import com.learnbae.my.presentation.base.BaseViewModel
import com.learnbae.my.presentation.screens.Screens

class MainActivityViewModel: BaseViewModel() {

    fun openRootScreen(){
        router.newRootScreen(Screens.getMainScreen())
    }
}
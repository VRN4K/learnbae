package com.learnbae.my

import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.learnbae.my.presentation.base.BaseViewModel

class TabContainerViewModel : BaseViewModel() {

    fun openFragment(screen: FragmentScreen){
        router.replaceScreen(screen)
    }

    fun returnToInitialFragment() {
        router.backTo(null)
    }
}
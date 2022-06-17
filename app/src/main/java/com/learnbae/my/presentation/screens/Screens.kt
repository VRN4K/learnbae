package com.learnbae.my.presentation.screens

import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.learnbae.my.presentation.screens.mainscreen.MainScreenFragment
import com.learnbae.my.presentation.screens.profilescreen.ProfileFragment

object Screens {
    fun getMainScreen() = FragmentScreen { MainScreenFragment() }
    fun getProfileScreen() = FragmentScreen { ProfileFragment() }
}
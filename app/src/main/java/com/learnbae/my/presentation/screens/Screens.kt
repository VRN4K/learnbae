package com.learnbae.my.presentation.screens

import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.learnbae.my.presentation.screens.mainscreen.MainScreenFragment

object Screens {
    fun getMainScreen() = FragmentScreen { MainScreenFragment() }
}
package com.learnbae.my.presentation.screens

import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.learnbae.my.presentation.screens.authscreen.AuthFragment
import com.learnbae.my.presentation.screens.mainscreen.MainScreenFragment
import com.learnbae.my.presentation.screens.vocabularyscreen.VocabularyFragment
import com.learnbae.my.presentation.screens.profilescreen.ProfileFragment
import com.learnbae.my.presentation.screens.registrationscreen.RegistrationFragment

object Screens {
    fun getMainScreen() = FragmentScreen { MainScreenFragment() }
    fun getProfileScreen() = FragmentScreen { ProfileFragment() }
    fun getVocabularyScreen() = FragmentScreen { VocabularyFragment() }
    fun getAuthScreen() = FragmentScreen { AuthFragment() }
    fun getRegistrationScreen() = FragmentScreen { RegistrationFragment() }
}
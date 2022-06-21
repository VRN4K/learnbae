package com.learnbae.my.presentation.screens

import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.learnbae.my.presentation.screens.mainscreen.MainScreenFragment
import com.learnbae.my.presentation.screens.vocabularyscreen.VocabularyFragment

object Screens {
    fun getMainScreen() = FragmentScreen { MainScreenFragment() }
    fun getVocabularyScreen() = FragmentScreen { VocabularyFragment() }
}
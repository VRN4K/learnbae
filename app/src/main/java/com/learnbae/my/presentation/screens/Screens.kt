package com.learnbae.my.presentation.screens

import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.learnbae.my.domain.datacontracts.model.UserProfileInfoUIModel
import com.learnbae.my.presentation.screens.authscreen.AuthFragment
import com.learnbae.my.presentation.screens.changepasswordscreen.ChangePasswordFragment
import com.learnbae.my.presentation.screens.emailsendcodescreen.EmailSendCodeFragment
import com.learnbae.my.presentation.screens.mainscreen.MainScreenFragment
import com.learnbae.my.presentation.screens.vocabularyscreen.VocabularyFragment
import com.learnbae.my.presentation.screens.profilescreen.ProfileFragment
import com.learnbae.my.presentation.screens.registrationscreen.RegistrationFragment
import com.learnbae.my.presentation.screens.resetpasswordscreen.ResetPasswordFragment
import com.learnbae.my.presentation.screens.searchtranslationscreen.SearchResultFragment
import com.learnbae.my.presentation.screens.updateprofilescreen.UpdateProfileFragment

object Screens {
    fun getMainScreen() = FragmentScreen { MainScreenFragment() }
    fun getProfileScreen() = FragmentScreen { ProfileFragment() }
    fun getVocabularyScreen() = FragmentScreen { VocabularyFragment() }
    fun getAuthScreen() = FragmentScreen { AuthFragment() }
    fun getRegistrationScreen() = FragmentScreen { RegistrationFragment() }
    fun getChangePasswordScreen() = FragmentScreen { ChangePasswordFragment() }
    fun getEmailSendCodeFragment() = FragmentScreen { EmailSendCodeFragment() }

    fun getUpdateProfileScreen(userProfileInfo: UserProfileInfoUIModel) = FragmentScreen {
        UpdateProfileFragment.newInstance(userProfileInfo)
    }

    fun getSearchResultFragment(sourceLang: String, targetLang: String, word: String) =
        FragmentScreen {
            SearchResultFragment.newInstance(sourceLang, targetLang, word)
        }

    fun getResetPasswordFragment(code: String?) =
        FragmentScreen { ResetPasswordFragment.newInstance(code) }


}

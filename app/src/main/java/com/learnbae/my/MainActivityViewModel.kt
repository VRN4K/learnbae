package com.learnbae.my

import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.google.gson.Gson
import com.learnbae.my.data.storage.preferences.StringPreference
import com.learnbae.my.domain.interfaces.ITranslationInteractor
import com.learnbae.my.presentation.base.BaseViewModel
import com.learnbae.my.presentation.screens.Screens
import ltst.nibirualert.my.domain.launchIO
import org.koin.core.component.inject
import java.util.*

class MainActivityViewModel : BaseViewModel() {
    companion object {
        const val MILLIS_IN_ONE_DAY = 86_400_000
        const val authKeyStore = "AUTH_KEY_STORE"
        const val authKeyPair = "AUTH_KEY_MAP"
        var AUTH_KEY: String? = null
    }

    private val sharedPreferences: StringPreference by inject()
    private val interactor: ITranslationInteractor by inject()

    init {
      //launchIO { getAuthKey() }
    }

    fun openRootScreen() {
        router.newRootScreen(Screens.getMainScreen())
    }

    fun openFragment(screen: FragmentScreen) {
        router.replaceScreen(screen)
    }

    private suspend fun getAuthKey() {
        val authPair: Pair<String, Long>? = null
        val currentDateTime = Calendar.getInstance().time.time

        Gson().fromJson(
            sharedPreferences.getValue(),
            Pair::class.java
        )
//добавить + сутки
        authPair?.let {
            if (it.second < currentDateTime) {
                AUTH_KEY = interactor.getAuthKey()
                sharedPreferences.set(Gson().toJson(Pair(AUTH_KEY, currentDateTime)))
            } else {
                AUTH_KEY = it.first
            }
        } ?: with(interactor.getAuthKey()) {
            sharedPreferences.set(Gson().toJson(Pair(this, currentDateTime)))
        }
    }
}
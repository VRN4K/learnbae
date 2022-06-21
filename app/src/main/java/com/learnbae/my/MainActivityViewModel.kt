package com.learnbae.my

import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.google.gson.Gson
import com.learnbae.my.data.net.retrofit.VocabularyInterceptor.Companion.MILLIS_IN_ONE_DAY
import com.learnbae.my.data.storage.preferences.StringPreference
import com.learnbae.my.domain.interfaces.ITranslationInteractor
import com.learnbae.my.presentation.base.BaseViewModel
import com.learnbae.my.presentation.screens.Screens
import ltst.nibirualert.my.domain.launchIO
import org.koin.core.component.inject
import java.util.*

class MainActivityViewModel : BaseViewModel() {
    private val interactor: ITranslationInteractor by inject()
    private val sharedPreferences: StringPreference by inject()

    fun openRootScreen() {
        router.newRootScreen(Screens.getMainScreen())
    }

    fun openFragment(screen: FragmentScreen) {
        router.replaceScreen(screen)
    }

    fun setNewAuthKey() {
        val currentDateTime = Calendar.getInstance().time.time
        val authPair = Gson().fromJson(
            sharedPreferences.getValue(),
            Pair::class.java
        ) as Pair<String, Long>?

        launchIO {
            authPair?.let {
                if (it.second < currentDateTime) {
                    sharedPreferences.set(
                        Gson().toJson(
                            Pair(
                                interactor.getAuthKey(),
                                currentDateTime.plus(MILLIS_IN_ONE_DAY)
                            )
                        )
                    )
                }
            } ?: with(interactor.getAuthKey()) {
                sharedPreferences.set(
                    Gson().toJson(
                        Pair(
                            this,
                            currentDateTime.plus(MILLIS_IN_ONE_DAY)
                        )
                    )
                )
            }
        }
    }
}
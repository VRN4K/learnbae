package com.learnbae.my.presentation.base

import androidx.lifecycle.ViewModel
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.learnbae.my.data.storage.preferences.AuthorizationPreferenceRepository
import com.learnbae.my.presentation.common.exceptions.createExceptionHandler
import com.learnbae.my.presentation.screens.Screens
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import okhttp3.OkHttpClient
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.qualifier.named
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel : ViewModel(), KoinComponent, CoroutineScope {
    private val router: Router by inject()
    private val authPreferenceRepository: AuthorizationPreferenceRepository by inject(
        qualifier = named(
            "TokenPreference"
        )
    )
    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext = Dispatchers.IO + job
    protected val handler by lazy { createExceptionHandler(::onException) }
    protected val okHttpClient: OkHttpClient by inject(qualifier = named("DefaultClient"))

    open fun openRootScreen() {
        router.newRootScreen(Screens.getMainScreen())
    }

    open suspend fun isUserAuthorized(): Boolean {
        return !authPreferenceRepository.getToken().isNullOrEmpty()
    }

    open fun openFragment(screen: FragmentScreen) {
        router.replaceScreen(screen)
    }

    open fun navigateToPreviousScreen(screen: FragmentScreen) {
        router.navigateTo(screen)
    }

    open fun navigateToPreviousScreen() {
        router.exit()
    }

    open fun onException(throwable: Throwable) {
        throwable.printStackTrace()
    }
}
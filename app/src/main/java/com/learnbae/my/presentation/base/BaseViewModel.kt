package com.learnbae.my.presentation.base

import androidx.lifecycle.ViewModel
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.learnbae.my.di.InteractorModule
import com.learnbae.my.domain.datacontracts.interfaces.IAuthorizationStorageRepository
import com.learnbae.my.presentation.common.exceptions.createExceptionHandler
import com.learnbae.my.presentation.screens.Screens
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import okhttp3.OkHttpClient
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel : ViewModel(), CoroutineScope {
    @Inject
    lateinit var router: Router

    @Inject
    lateinit var authPreferenceRepository: IAuthorizationStorageRepository

    @Inject
    @InteractorModule.DefaultClient
    lateinit var okHttpClient: OkHttpClient

    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext = Dispatchers.IO + job
    protected val handler by lazy { createExceptionHandler(::onException) }

    open fun openRootScreen() {
        router.newRootScreen(Screens.getMainScreen())
    }

    open suspend fun isUserAuthorized(): Boolean {
        return !authPreferenceRepository.getToken().isNullOrEmpty()
    }

    open fun openFragment(screen: FragmentScreen) {
        router.replaceScreen(screen)
    }

    open fun navigateToScreen(screen: FragmentScreen) {
        router.navigateTo(screen)
    }

    open fun navigateToPreviousScreen() {
        router.exit()
    }

    open fun onException(throwable: Throwable) {
        throwable.printStackTrace()
    }
}
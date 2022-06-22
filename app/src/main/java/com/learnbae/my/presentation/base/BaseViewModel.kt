package com.learnbae.my.presentation.base

import androidx.lifecycle.ViewModel
import com.github.terrakok.cicerone.Router
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
    protected val router: Router by inject()
    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext = Dispatchers.IO + job
    protected val okHttpClient: OkHttpClient by inject(qualifier = named("DefaultClient"))

    open fun navigateToMainPage() {
        router.navigateTo(Screens.getMainScreen())
    }

    open fun navigateToPreviousScreen() {
        router.exit()
    }
}
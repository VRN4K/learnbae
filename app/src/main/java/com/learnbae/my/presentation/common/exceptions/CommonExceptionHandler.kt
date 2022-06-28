package com.learnbae.my.presentation.common.exceptions

import com.learnbae.my.presentation.base.BaseViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import java.io.IOException
import java.net.UnknownServiceException

class CommonExceptionHandler(
    private val onException: ((Throwable) -> Unit)? = null,
) {
    val coroutineHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
        onException?.invoke(throwable)
    }
}


fun BaseViewModel.createExceptionHandler(onException: (Throwable) -> Unit) =
    CommonExceptionHandler(onException).coroutineHandler

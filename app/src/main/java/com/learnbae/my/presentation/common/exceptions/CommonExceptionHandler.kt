package com.learnbae.my.presentation.common.exceptions

import kotlinx.coroutines.CoroutineExceptionHandler

class CommonExceptionHandler(
    private val onException: ((Throwable) -> Unit)? = null,
) {
    val coroutineHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
        onException?.invoke(throwable)
    }
}


fun createExceptionHandler(onException: (Throwable) -> Unit) =
    CommonExceptionHandler(onException).coroutineHandler

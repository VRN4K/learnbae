package com.learnbae.my.presentation.common.extensions

fun <T> List<T>.indexOfOrNull(element: T): Int? {
    val index = indexOf(element)
    return if (index == -1) null else index
}

fun String.splitTranslation(): List<String> {
    val separator = ", "
    return this.split(separator)
}

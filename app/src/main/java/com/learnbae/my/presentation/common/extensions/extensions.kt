package com.learnbae.my.presentation.common.extensions

import com.learnbae.my.data.net.model.SearchResultModel
import com.learnbae.my.domain.datacontracts.model.VocabularyWordUI
import java.util.*

fun <T> List<T>.indexOfOrNull(element: T): Int? {
    val index = indexOf(element)
    return if (index == -1) null else index
}

fun String.splitTranslation(): List<String> {
    val separator = ", "
    return this.split(separator)
}

package com.learnbae.my.presentation.common

import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import com.google.android.material.textfield.TextInputLayout
import com.google.gson.Gson

fun TextInputLayout.showError(errorText: String = "") {
    this.error = errorText
}

fun View.setVisibility(isVisible: Boolean = true) {
    this.visibility = if (isVisible) VISIBLE else GONE
}
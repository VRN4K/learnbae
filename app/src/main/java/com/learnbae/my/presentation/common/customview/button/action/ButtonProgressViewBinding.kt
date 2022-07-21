package com.learnbae.my.presentation.common.customview.button.action

import android.widget.ProgressBar
import androidx.appcompat.widget.AppCompatButton
import androidx.viewbinding.ViewBinding

abstract class ButtonProgressViewBinding(private val binding: ViewBinding) : ViewBinding {
    override fun getRoot() = binding.root

    abstract val progress: ProgressBar
    abstract val button: AppCompatButton
}
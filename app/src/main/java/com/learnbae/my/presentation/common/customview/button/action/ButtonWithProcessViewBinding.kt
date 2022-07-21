package com.learnbae.my.presentation.common.customview.button.action

import android.widget.ProgressBar
import androidx.appcompat.widget.AppCompatButton


class ButtonWithProcessViewBinding(private val binding: ButtonWithProcessViewBinding) :
    ButtonProgressViewBinding(binding) {

    override val progress: ProgressBar by lazy { binding.progress }
    override val button: AppCompatButton by lazy { binding.button }
}
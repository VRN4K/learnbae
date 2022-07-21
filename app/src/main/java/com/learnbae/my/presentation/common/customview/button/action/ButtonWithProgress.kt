package com.learnbae.my.presentation.common.customview.button.action

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.learnbae.my.R
import com.learnbae.my.databinding.ButtonWithProgressBinding
import com.learnbae.my.presentation.common.setVisibility
import com.learnbae.my.presentation.common.toEmptyOnNullString

class ButtonWithProgress @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    val binding = ButtonWithProgressBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        attrs.apply {
            val typedArray = context.obtainStyledAttributes(
                this,
                R.styleable.ButtonWithProgress,
                0,
                0
            )
            if (typedArray.hasValue(R.styleable.ButtonWithProgress_button_text)) {
                buttonText = typedArray.getString(R.styleable.ButtonWithProgress_button_text)!!
            }
            typedArray.recycle()
        }
    }

    var buttonText: String
        set(value) {
            binding.button.text = value
            field = value
        }

    fun showLoading() {
        binding.apply {
            button.text = buttonText.toEmptyOnNullString()
            buttonProgress.setVisibility(true)
            button.isEnabled = false
        }
    }

    fun hideLoading() {
        binding.apply {
            button.text = buttonText
            buttonProgress.setVisibility(false)
            button.isEnabled = true
        }
    }
}

interface ButtonWithProgressCallback {
    fun onButtonClick()
}

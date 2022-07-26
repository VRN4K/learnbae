package com.learnbae.my.presentation.common.customview.button.action

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.learnbae.my.R
import com.learnbae.my.databinding.ButtonWithProgressBinding
import com.learnbae.my.presentation.common.setVisibility

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

    fun showLoading() {
        binding.apply {
            button.setTextColor(
                resources.getColor(
                    com.google.android.material.R.color.mtrl_btn_transparent_bg_color,
                    context.theme
                )
            )
            buttonProgress.setVisibility(true)
            button.isEnabled = false
        }
    }

    fun hideLoading() {
        binding.apply {
            button.text = buttonText
            button.setTextColor(resources.getColor(R.color.primary_light_text, context.theme))
            buttonProgress.setVisibility(false)
            button.isEnabled = true
        }
    }

    fun setOnClickListener(action: () -> Unit) {
        binding.button.setOnClickListener { action() }
    }
}

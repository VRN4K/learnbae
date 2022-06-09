package com.learnbae.my.presentation.common.animator

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.annotation.IdRes
import com.learnbae.my.databinding.ProgressBarBinding

class LoadingViewAnimator(
    context: Context,
    attrs: AttributeSet? = null
) : BetterViewAnimator(context, attrs) {

    private val loadingBinding by lazy {
        ProgressBarBinding.inflate(
            LayoutInflater.from(context),
            null,
            false
        )
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        showLoading()
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        addView(loadingBinding.root)
    }

    fun showLoading() {
        visibleChildId = loadingBinding.root.id
    }

    fun changeLoadingState(isLoading: Boolean, @IdRes contentId: Int) {
        if (isLoading) showLoading() else visibleChildId = contentId
    }

    fun changeProgressGravity(newGravity: Int) {
        loadingBinding.progressBarLoading.apply {
            layoutParams = LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT
            ).apply {
                gravity = newGravity
            }
        }
    }
}
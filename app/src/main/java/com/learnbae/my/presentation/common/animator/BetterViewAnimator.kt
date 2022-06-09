package com.learnbae.my.presentation.common.animator

import android.content.Context
import android.util.AttributeSet
import android.widget.ViewAnimator
import com.learnbae.my.R

open class BetterViewAnimator @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : ViewAnimator(context, attrs) {

    init {
        attrs.apply {
            val typedArray = context.obtainStyledAttributes(
                this,
                R.styleable.BetterViewAnimator,
                0,
                0
            )
            if (typedArray.hasValue(R.styleable.BetterViewAnimator_visible_child)) {
                visibleChildId = typedArray.getResourceId(
                    R.styleable.BetterViewAnimator_visible_child,
                    0
                )
            }
            typedArray.recycle()
        }
    }

    var visibleChildId: Int
        get() = getChildAt(displayedChild).id
        set(id) {
            if (visibleChildId == id) return
            for (i in 0 until childCount) {
                if (getChildAt(i).id == id) {
                    displayedChild = i
                    return
                }
            }
            throw IllegalArgumentException("No view with ID $id")
        }
}
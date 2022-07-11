package com.learnbae.my.presentation.base

import androidx.fragment.app.Fragment
import com.learnbae.my.MainActivity

abstract class BaseFragment : Fragment(), BaseView {
    override fun setNavigationVisibility(isVisible: Boolean) {
        (activity as MainActivity).setNavigationVisibility(isVisible)
    }
}
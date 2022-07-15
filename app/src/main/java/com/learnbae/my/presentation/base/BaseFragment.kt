package com.learnbae.my.presentation.base

import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.learnbae.my.MainActivity
import com.learnbae.my.presentation.base.dialogs.ConfirmActionDialogFragment

abstract class BaseFragment : Fragment(), BaseView {
    override fun setNavigationVisibility(isVisible: Boolean) {
        (activity as MainActivity).setNavigationVisibility(isVisible)
    }

    fun showConfirmActionDialog(
        message: String,
        confirmButtonText: String,
        cancelButtonText: String,
        confirmAction: () -> Unit,
        cancelAction: (() -> Unit)? = null
    ) {
        ConfirmActionDialogFragment().apply {
            setActionListener(object : ConfirmActionDialogFragment.ConfirmActionDialogListener {
                override fun showMessageText(textField: TextView) {
                    textField.text = message
                }

                override fun showConfirmButtonText(confirmButton: Button) {
                    confirmButton.text = confirmButtonText
                    super.showConfirmButtonText(confirmButton)
                }

                override fun showCancelButtonText(cancelButton: Button) {
                    cancelButton.text = cancelButtonText
                    super.showCancelButtonText(cancelButton)
                }

                override fun onConfirmButtonClick() {
                    confirmAction()
                    super.onConfirmButtonClick()
                }

                override fun onCancelButtonClick() {
                    cancelAction?.let { it() }
                    super.onCancelButtonClick()
                }
            })
        }.show(requireActivity().supportFragmentManager, "ConfirmActionDialogFragmentTag")
    }
}
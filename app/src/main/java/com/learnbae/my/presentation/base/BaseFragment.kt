package com.learnbae.my.presentation.base

import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.learnbae.my.MainActivity
import com.learnbae.my.presentation.base.dialogs.ConfirmActionDialogFragment
import com.learnbae.my.presentation.base.dialogs.InformationDialogFragment

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

    fun showInformationDialog(
        messageText: String,
        messageSubText: String? = null,
    ) {
        InformationDialogFragment().apply {
            setActionListener(object : InformationDialogFragment.InformationDialogListener {
                override fun showMessageText(textField: TextView) {
                    textField.text = messageText
                }

                override fun showMessageSubText(textField: TextView) {
                    textField.text = messageSubText
                }
            })
        }.show(requireActivity().supportFragmentManager, "ConfirmActionDialogFragmentTag")
    }
}
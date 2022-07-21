package com.learnbae.my.presentation.base

import androidx.fragment.app.Fragment
import com.learnbae.my.MainActivity
import com.learnbae.my.presentation.base.dialogs.ConfirmActionDialogFragment
import com.learnbae.my.presentation.base.dialogs.InformationDialogFragment

abstract class BaseFragment : Fragment(), BaseView {
    override fun setNavigationVisibility(isVisible: Boolean) {
        (activity as MainActivity).setNavigationVisibility(isVisible)
    }

    private val confirmationDialog = ConfirmActionDialogFragment()
    private val informationDialog = InformationDialogFragment()

    fun showConfirmActionDialog(
        message: String,
        confirmButtonText: String,
        cancelButtonText: String,
        confirmAction: () -> Unit,
        cancelAction: (() -> Unit)? = null
    ) {
        confirmationDialog.apply {
            setActionListener(object : ConfirmActionDialogFragment.ConfirmActionDialogListener {

                override fun onConfirmButtonClick() {
                    confirmAction()
                    super.onConfirmButtonClick()
                }

                override fun onCancelButtonClick() {
                    cancelAction?.let { it() }
                    super.onCancelButtonClick()
                }
            })
        }.show(
            requireActivity().supportFragmentManager, "ConfirmActionDialogFragmentTag",
            message,
            confirmButtonText, cancelButtonText
        )
    }

    fun showInformationDialog(
        messageText: String,
        messageSubText: String? = null,
    ) {
        informationDialog.show(
            requireActivity().supportFragmentManager,
            "InformationDialogFragmentTag",
            messageText, messageSubText
        )
    }
}
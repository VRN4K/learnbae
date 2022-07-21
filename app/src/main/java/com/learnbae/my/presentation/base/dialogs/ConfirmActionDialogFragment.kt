package com.learnbae.my.presentation.base.dialogs

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.learnbae.my.databinding.ActionConfirmationDialogLayoutBinding
import ltst.nibirualert.my.presentation.common.onDestroyNullable

class ConfirmActionDialogFragment : DialogFragment() {
    private var binding by onDestroyNullable<ActionConfirmationDialogLayoutBinding>()
    private var listener: ConfirmActionDialogListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ActionConfirmationDialogLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog!!.window!!.attributes.width = ViewGroup.LayoutParams.MATCH_PARENT
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setListeners()
    }

    private fun setListeners() {
        binding.apply {
            confirmButton.setOnClickListener {
                listener!!.onConfirmButtonClick()
                dialog!!.dismiss()
            }

            cancelButton.setOnClickListener {
                listener!!.onCancelButtonClick()
                dialog!!.dismiss()
            }
        }
    }

    fun setActionListener(listener: ConfirmActionDialogListener) {
        this.listener = listener
    }

    fun show(
        manager: FragmentManager,
        tag: String?,
        messageText: String,
        confirmButtonText: String,
        cancelButtonText: String
    ) {
        binding.messageText.text = messageText
        binding.confirmButton.text = confirmButtonText
        binding.cancelButton.text = cancelButtonText
        super.show(manager, tag)
    }

    interface ConfirmActionDialogListener {
        fun onConfirmButtonClick() {}
        fun onCancelButtonClick() {}
    }
}
package com.learnbae.my.presentation.base.dialogs

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.learnbae.my.databinding.InformationDialogLayoutBinding
import ltst.nibirualert.my.presentation.common.onDestroyNullable

class InformationDialogFragment() : DialogFragment() {
    private var binding by onDestroyNullable<InformationDialogLayoutBinding>()
    private var listener: InformationDialogListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = InformationDialogLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog!!.window!!.attributes.width = ViewGroup.LayoutParams.MATCH_PARENT
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        listener?.apply {
            binding.apply {
                showMessageText(messageText)
                showMessageSubText(messageSubText)
            }
        }
    }

    fun setActionListener(listener: InformationDialogListener) {
        this.listener = listener
    }

    interface InformationDialogListener {
        fun showMessageText(textField: TextView) {}
        fun showMessageSubText(textField: TextView) {}
    }
}
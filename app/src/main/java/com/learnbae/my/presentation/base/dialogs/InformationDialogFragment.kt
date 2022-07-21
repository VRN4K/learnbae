package com.learnbae.my.presentation.base.dialogs

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.learnbae.my.databinding.InformationDialogLayoutBinding
import ltst.nibirualert.my.presentation.common.onDestroyNullable

class InformationDialogFragment() : DialogFragment() {
    private var binding by onDestroyNullable<InformationDialogLayoutBinding>()

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
    }


    fun show(manager: FragmentManager, tag: String?, messageText: String, messageSubText: String?) {
        binding.apply {
            this.messageText.text = messageText
            messageSubText?.let { this.messageText.text = messageSubText }
        }
        super.show(manager, tag)
    }
}
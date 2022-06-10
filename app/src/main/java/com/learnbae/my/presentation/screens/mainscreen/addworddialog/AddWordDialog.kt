package com.learnbae.my.presentation.screens.mainscreen.addworddialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.learnbae.my.databinding.AddWordDialogBinding
import ltst.nibirualert.my.presentation.common.onDestroyNullable

class AddWordDialog : DialogFragment() {
    private var binding by onDestroyNullable<AddWordDialogBinding>()
    private var listener: AddButtonClickListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = AddWordDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog!!.window!!.attributes.width = ViewGroup.LayoutParams.MATCH_PARENT
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.apply {
            addButton.setOnClickListener {
                listener!!.onClickWordAdd(
                    textFieldWord.editText!!.text.toString(),
                    textFieldTranslation.editText!!.text.toString()
                )
                dialog?.dismiss()
            }
        }
        dialog!!.setOnCancelListener {
            dialog?.dismiss()
        }
    }

    fun setActionListener(listener: AddButtonClickListener) {
        this.listener = listener
    }

    interface AddButtonClickListener {
        fun onClickWordAdd(wordText: String, wordTranslation: String) {}
    }
}
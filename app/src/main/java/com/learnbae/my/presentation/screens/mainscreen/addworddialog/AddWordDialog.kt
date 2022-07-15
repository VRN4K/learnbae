package com.learnbae.my.presentation.screens.mainscreen.addworddialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.google.android.material.textfield.TextInputLayout
import com.learnbae.my.R
import com.learnbae.my.databinding.AddWordDialogBinding
import ltst.nibirualert.my.presentation.common.onDestroyNullable

class AddWordDialog : DialogFragment() {
    private var binding by onDestroyNullable<AddWordDialogBinding>()
    private var listener: AddButtonClickListener? = null
    private val inputFields by lazy {
        listOf(
            binding.textFieldWord,
            binding.textFieldTranslation
        )
    }

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
        setListeners()
    }

    private fun setListeners() {
        binding.apply {
            addWordButton.setOnClickListener {
                when {
                    inputFields.all { it.editText!!.text.isNotEmpty() } -> listener!!.onClickWordAdd(
                        textFieldWord.editText!!.text.toString(),
                        textFieldTranslation.editText!!.text.toString()
                    ).also {
                        dialog?.dismiss()
                    }
                    else -> inputFields.onEach {
                        if (it.editText!!.text.isEmpty()) it.showError(Errors.EMPTY_TEXT_INPUT) else it.hideError()
                    }
                }
            }
        }

        dialog!!.setOnCancelListener {
            dialog?.dismiss()
        }
    }

    private fun TextInputLayout.showError(error: Errors) {
        this.error = getString(error.errorText)
    }

    private fun TextInputLayout.hideError() {
        this.error = ""
    }

    fun setActionListener(listener: AddButtonClickListener) {
        this.listener = listener
    }

    interface AddButtonClickListener {
        fun onClickWordAdd(wordText: String, wordTranslation: String) {}
    }
}

enum class Errors(val errorText: Int) {
    EMPTY_TEXT_INPUT(R.string.empty_input_error_text)
}
package com.learnbae.my.presentation.screens.emailsendcodescreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.learnbae.my.databinding.EmailSendCodeLayoutBinding
import com.learnbae.my.presentation.base.BaseFragment
import com.learnbae.my.presentation.common.showError
import dagger.hilt.android.AndroidEntryPoint
import ltst.nibirualert.my.presentation.common.onDestroyNullable

@AndroidEntryPoint
class EmailSendCodeFragment : BaseFragment() {
    private var binding by onDestroyNullable<EmailSendCodeLayoutBinding>()
    private val viewModel by viewModels<EmailSendCodeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = EmailSendCodeLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setListeners()
        setObservers()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun setListeners() {
        binding.apply {
            backButton.setOnClickListener { viewModel.navigateToPreviousScreen() }
            sendCodeButton.setOnClickListener { viewModel.sendEmailPasswordResetMessage(textEmail.editText?.text.toString()) }
        }
    }

    private fun setObservers() {
        binding.apply {
            viewModel.apply {
                emailError.observe(viewLifecycleOwner) {
                    textEmail.showError(it?.let { textId -> resources.getString(textId) }
                        ?: "")
                }

                dialogMessage.observe(viewLifecycleOwner) {
                    it?.let {
                        showInformationDialog(
                            resources.getString(it)
                        )
                    }
                }
            }
        }
    }
}
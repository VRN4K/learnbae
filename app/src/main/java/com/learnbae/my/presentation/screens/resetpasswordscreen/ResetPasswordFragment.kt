package com.learnbae.my.presentation.screens.resetpasswordscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.learnbae.my.R
import com.learnbae.my.databinding.ResetPasswordLayoutBinding
import com.learnbae.my.presentation.base.BaseFragment
import com.learnbae.my.presentation.common.showError
import dagger.hilt.android.AndroidEntryPoint
import ltst.nibirualert.my.presentation.common.onDestroyNullable

@AndroidEntryPoint
class ResetPasswordFragment : BaseFragment() {
    companion object {
        private const val KEY = "RESET PASSWORD"
        private const val KEY_SCREEN = "IS CODE EMPTY?"
        fun newInstance(code: String?) = ResetPasswordFragment().apply {
            arguments = if (code.isNullOrEmpty()) {
                Bundle().apply {
                    putBoolean(KEY_SCREEN, true)
                }
            } else {
                Bundle().apply {
                    putBoolean(KEY_SCREEN, false)
                    putString(KEY, code)
                }
            }
        }
    }

    private var binding by onDestroyNullable<ResetPasswordLayoutBinding>()
    private val viewModel by viewModels<ResetPasswordViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ResetPasswordLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        showContent(requireArguments().getBoolean(KEY_SCREEN))
        setListeners()
        setObservers()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun setListeners() {
        binding.backButton.setOnClickListener { viewModel.navigateToPreviousScreen() }
    }

    private fun setObservers() {
        binding.apply {
            viewModel.apply {
                emailError.observe(viewLifecycleOwner) {
                    textEmail.showError(it?.let { textId -> resources.getString(textId) }
                        ?: "")
                }

                passwordError.observe(viewLifecycleOwner) {
                    textNewPassword.showError(it?.let { textId -> resources.getString(textId) }
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

    private fun showContent(isCodeNotNull: Boolean) {
        binding.apply {
            if (isCodeNotNull) {
                viewAnimator.visibleChildId = emailField.id
                changePasswordButton.setOnClickListener {
                    viewModel.sendEmailPasswordResetMessage(
                        textEmail.editText?.text.toString()
                    )
                    changePasswordButton.text =
                        resources.getText(R.string.reset_password_change_password_button)
                }
            } else {
                viewAnimator.visibleChildId = passwordField.id
                changePasswordButton.setOnClickListener {
                    viewModel.resetPassword(
                        requireArguments().getString(KEY)!!,
                        binding.textNewPassword.editText!!.text.toString()
                    )
                    changePasswordButton.text =
                        resources.getText(R.string.reset_password_change_password_button)
                }
            }
        }
    }
}
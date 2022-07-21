package com.learnbae.my.presentation.screens.resetpasswordscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.learnbae.my.databinding.ResetPasswordLayoutBinding
import com.learnbae.my.presentation.base.BaseFragment
import com.learnbae.my.presentation.common.showError
import dagger.hilt.android.AndroidEntryPoint
import ltst.nibirualert.my.presentation.common.onDestroyNullable

@AndroidEntryPoint
class ResetPasswordFragment : BaseFragment() {
    companion object {
        private const val KEY = "RESET PASSWORD"
        fun newInstance(code: String?) = ResetPasswordFragment().apply {
            arguments = Bundle().apply {
                putString(KEY, code)
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
        viewModel.checkIsCodeValid(requireArguments().getString(KEY)!!)
        setListeners()
        setObservers()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun setListeners() {
        binding.apply {
            backButton.setOnClickListener { viewModel.navigateToPreviousScreen() }
            changePasswordButton.setOnClickListener {
                viewModel.resetPassword(
                    requireArguments().getString(
                        KEY
                    )!!, textNewPassword.editText?.text.toString()
                )
            }
        }
    }

    private fun setObservers() {
        binding.apply {
            viewModel.apply {
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

                isCodeValid.observe(viewLifecycleOwner) {
                    it?.let { showContent(it) }
                }
            }
        }
    }

    private fun showContent(isCodeValid: Boolean?) {
        binding.apply {
            if (isCodeValid!!) {
                viewAnimator.visibleChildId = resetPasswordContent.id
            } else {
                viewAnimator.visibleChildId = codeIsInvalidTitle.id
            }
        }
    }
}
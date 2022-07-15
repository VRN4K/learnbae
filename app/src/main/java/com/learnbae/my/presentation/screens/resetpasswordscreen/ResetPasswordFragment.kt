package com.learnbae.my.presentation.screens.resetpasswordscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.learnbae.my.databinding.ResetPasswordLayoutBinding
import com.learnbae.my.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import ltst.nibirualert.my.presentation.common.onDestroyNullable

@AndroidEntryPoint
class ResetPasswordFragment : BaseFragment() {
    companion object {
        private const val KEY = "RESET PASSWORD"
        fun newInstance(code: String) = ResetPasswordFragment().apply {
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
        setListeners()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun setListeners() {
        binding.backButton.setOnClickListener { viewModel.navigateToPreviousScreen() }
        binding.changePasswordButton.setOnClickListener {
            viewModel.resetPassword(
                requireArguments().getString(KEY)!!,
                binding.textNewPassword.editText!!.text.toString()
            )
        }
    }
}
package com.learnbae.my.presentation.screens.changepasswordscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.learnbae.my.data.storage.entities.PasswordChangeModel
import com.learnbae.my.databinding.ChangePasswordLayoutBinding
import com.learnbae.my.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import ltst.nibirualert.my.presentation.common.onDestroyNullable

@AndroidEntryPoint
class ChangePasswordFragment : BaseFragment() {
    private var binding by onDestroyNullable<ChangePasswordLayoutBinding>()
    private val viewModel by viewModels<ChangePasswordViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ChangePasswordLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setListeners()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun setListeners() {
        binding.apply {
            backButton.setOnClickListener { viewModel.navigateToPreviousScreen() }
            changePasswordButton.setOnClickListener {
                viewModel.changePassword(
                    PasswordChangeModel(
                        textCurrentPassword.editText?.text.toString(),
                        textNewPassword.editText?.text.toString()
                    )
                )
            }
        }
    }
}
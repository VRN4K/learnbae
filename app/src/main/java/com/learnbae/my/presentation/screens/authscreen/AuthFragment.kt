package com.learnbae.my.presentation.screens.authscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.learnbae.my.databinding.AuthorizationLayoutBinding
import com.learnbae.my.presentation.common.setVisibility
import com.learnbae.my.presentation.common.showError
import com.learnbae.my.presentation.screens.Screens
import dagger.hilt.android.AndroidEntryPoint
import ltst.nibirualert.my.presentation.common.onDestroyNullable

@AndroidEntryPoint
class AuthFragment : Fragment() {
    private var binding by onDestroyNullable<AuthorizationLayoutBinding>()
    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = AuthorizationLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setListeners()
        setObservers()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun setListeners() {
        binding.apply {
            addButton.setOnClickListener {
                authViewModel.singInByEmailAndPassword(
                    binding.textEmailField.editText!!.text.toString(),
                    binding.textPasswordField.editText!!.text.toString()
                )
            }
            registrationButton.setOnClickListener { authViewModel.navigateToPreviousScreen(Screens.getRegistrationScreen()) }
        }
    }

    private fun setObservers() {
        authViewModel.apply {
            userError.observe(viewLifecycleOwner) {
                it?.let { showUserError(true, resources.getString(it)) } ?: showUserError(false)
            }

            emailError.observe(viewLifecycleOwner) {
                binding.textEmailField.showError(it?.let { textId -> resources.getString(textId) }
                    ?: "")
            }

            passwordError.observe(viewLifecycleOwner) {
                binding.textPasswordField.showError(it?.let { textId -> resources.getString(textId) }
                    ?: "")
            }
        }
    }

    private fun showUserError(isShow: Boolean, errorText: String? = null) {
        binding.userErrorText.setVisibility(isShow)
        errorText?.let { binding.userErrorText.text = it }
    }

}
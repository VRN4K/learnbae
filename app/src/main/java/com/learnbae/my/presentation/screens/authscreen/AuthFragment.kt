package com.learnbae.my.presentation.screens.authscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.learnbae.my.databinding.AuthorizationLayoutBinding
import com.learnbae.my.presentation.common.setVisibility
import com.learnbae.my.presentation.common.showError
import com.learnbae.my.presentation.screens.Screens
import ltst.nibirualert.my.presentation.common.onDestroyNullable

class AuthFragment : Fragment() {
    private var binding by onDestroyNullable<AuthorizationLayoutBinding>()
    private val authViewModel by lazy { ViewModelProvider(this).get(AuthViewModel::class.java) }

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
                showUserError(true, it?.let { textId -> resources.getString(textId) } ?: "")
            }

            emailError.observe(viewLifecycleOwner) {
                binding.textEmailField.showError(it?.let { textId -> resources.getString(textId) } ?: "")
            }

            passwordError.observe(viewLifecycleOwner) {
                binding.textPasswordField.showError(it?.let { textId -> resources.getString(textId) } ?: "")
            }
        }
    }

    private fun showUserError(isShow: Boolean, errorText: String) {
        binding.userErrorText.text = errorText
        binding.userErrorText.setVisibility(isShow)
    }

}
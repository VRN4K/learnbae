package com.learnbae.my.presentation.screens.authscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.learnbae.my.R
import com.learnbae.my.databinding.AuthorizationLayoutBinding
import com.learnbae.my.presentation.base.BaseFragment
import com.learnbae.my.presentation.common.setVisibility
import com.learnbae.my.presentation.common.showError
import com.learnbae.my.presentation.screens.Screens
import dagger.hilt.android.AndroidEntryPoint
import ltst.nibirualert.my.presentation.common.onDestroyNullable

@AndroidEntryPoint
class AuthFragment : BaseFragment() {
    private var binding by onDestroyNullable<AuthorizationLayoutBinding>()
    private val viewModel by viewModels<AuthViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = AuthorizationLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.apply {
            logInButton.buttonText = resources.getString(R.string.authorization_login_button_text)
            logInButton.hideLoading()
        }
        setListeners()
        setObservers()
        setNavigationVisibility(true)
        super.onViewCreated(view, savedInstanceState)
    }

    private fun setListeners() {
        binding.apply {
            logInButton.setOnClickListener {
                viewModel.singInByEmailAndPassword(
                    textEmailField.editText!!.text.toString(),
                    textPasswordField.editText!!.text.toString()
                )
            }
            forgotPasswordButton.setOnClickListener {
                viewModel.navigateToScreen(Screens.getEmailSendCodeFragment())
            }

            registrationButton.setOnClickListener {
                viewModel.navigateToScreen(Screens.getRegistrationScreen())
            }
        }
    }

    private fun setObservers() {
        binding.apply {
            viewModel.apply {
                userError.observe(viewLifecycleOwner) {
                    it?.let { showUserError(true, resources.getString(it)) } ?: showUserError(false)
                }

                emailError.observe(viewLifecycleOwner) {
                    textEmailField.showError(it?.let { textId -> resources.getString(textId) }
                        ?: "")
                }

                passwordError.observe(viewLifecycleOwner) {
                    textPasswordField.showError(it?.let { textId ->
                        resources.getString(
                            textId
                        )
                    }
                        ?: "")
                }

                showButtonLoadingStatus.observe(viewLifecycleOwner) {
                    if (it) logInButton.showLoading() else logInButton.hideLoading()
                }
            }
        }
    }

    private fun showUserError(isShow: Boolean, errorText: String? = null) {
        binding.userErrorText.setVisibility(isShow)
        errorText?.let { binding.userErrorText.text = it }
    }
}
package com.learnbae.my.presentation.screens.registrationscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import com.learnbae.my.R
import com.learnbae.my.data.storage.entities.RegisterRequestData
import com.learnbae.my.data.storage.entities.RegisterUserInfo
import com.learnbae.my.data.storage.entities.UserEntity
import com.learnbae.my.databinding.RegistrationLayoutBinding
import com.learnbae.my.presentation.base.BaseFragment
import com.learnbae.my.presentation.common.showError
import dagger.hilt.android.AndroidEntryPoint
import ltst.nibirualert.my.presentation.common.onDestroyNullable

@AndroidEntryPoint
class RegistrationFragment : BaseFragment() {
    private var binding by onDestroyNullable<RegistrationLayoutBinding>()
    private val viewModel by viewModels<RegistrationViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = RegistrationLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setObservers()
        setListeners()
        binding.singUpButton.apply {
            buttonText = "Зарегистрироваться"
            hideLoading()
        }
        setNavigationVisibility(false)
        binding.englishLevelsDropdown.setAdapter(
            ArrayAdapter(
                requireContext(),
                R.layout.english_level_item,
                EnglishLevelsList.values().toMutableList()
            )
        )
        super.onViewCreated(view, savedInstanceState)
    }

    private fun setListeners() {
        binding.apply {
            singUpButton.setOnClickListener {
                viewModel.registerNewUser(
                    RegisterRequestData(
                        UserEntity(
                            textUsernameField.editText?.text.toString(),
                            textFullnameField.editText?.text.toString(),
                            dropdownEnglishLevel.editText!!.text.toString(),
                            textEmailField.editText?.text.toString()
                        ),
                        RegisterUserInfo(
                            textEmailField.editText?.text.toString(),
                            textPasswordField.editText?.text.toString()
                        )
                    )
                )
            }
            backButton.setOnClickListener { viewModel.navigateToPreviousScreen() }
        }
    }

    private fun setObservers() {
        binding.apply {
            viewModel.apply {
                usernameError.observe(viewLifecycleOwner) {
                    textUsernameField.showError(it?.let { textId -> resources.getString(textId) }
                        ?: "")
                }

                fullNameError.observe(viewLifecycleOwner) {
                    textFullnameField.showError(it?.let { textId -> resources.getString(textId) }
                        ?: "")
                }

                emailError.observe(viewLifecycleOwner) {
                    textEmailField.showError(it?.let { textId -> resources.getString(textId) }
                        ?: "")
                }

                passwordError.observe(viewLifecycleOwner) {
                    textPasswordField.showError(it?.let { textId -> resources.getString(textId) }
                        ?: "")
                }

                showButtonLoadingState.observe(viewLifecycleOwner) {
                    it?.let { if (it) singUpButton.showLoading() else singUpButton.hideLoading() }
                }
            }
        }
    }

    override fun onDestroyView() {
        setNavigationVisibility(true)
        super.onDestroyView()
    }
}

enum class EnglishLevelsList(val description: Int) {
    A1(R.string.a1_level_description),
    A2(R.string.a2_level_description),
    B1(R.string.b1_level_description),
    B2(R.string.b2_level_description),
    C1(R.string.c1_level_description),
    C2(R.string.c2_level_description)
}
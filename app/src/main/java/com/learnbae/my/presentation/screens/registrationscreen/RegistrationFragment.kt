package com.learnbae.my.presentation.screens.registrationscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.learnbae.my.R
import com.learnbae.my.data.storage.entities.RegisterRequestData
import com.learnbae.my.data.storage.entities.RegisterUserInfo
import com.learnbae.my.data.storage.entities.UserEntity
import com.learnbae.my.databinding.RegistrationLayoutBinding
import com.learnbae.my.presentation.common.showError
import ltst.nibirualert.my.presentation.common.onDestroyNullable

class RegistrationFragment : Fragment() {
    private var binding by onDestroyNullable<RegistrationLayoutBinding>()
    private val registrationViewModel by lazy { ViewModelProvider(this).get(RegistrationViewModel::class.java) }

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
            addButton.setOnClickListener {
                registrationViewModel.registerNewUser(
                    RegisterRequestData(
                        UserEntity(
                            textUserNameField.editText?.text.toString(),
                            textFullNameField.editText?.text.toString(),
                            dropDownEnglishLevel.editText!!.text.toString(),
                            textEmailField.editText?.text.toString()
                        ),
                        RegisterUserInfo(
                            textEmailField.editText?.text.toString(),
                            textPasswordField.editText?.text.toString()
                        )
                    )
                )
            }
        }
    }

    private fun setObservers() {
        binding.apply {
            registrationViewModel.apply {
                usernameError.observe(viewLifecycleOwner) {
                    textUserNameField.showError(it?.let { textId -> resources.getString(textId) }
                        ?: "")
                }

                fullNameError.observe(viewLifecycleOwner) {
                    textFullNameField.showError(it?.let { textId -> resources.getString(textId) }
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
            }
        }
    }
}

enum class EnglishLevelsList {
    A1, A2, B1, B2, C1, C2
}
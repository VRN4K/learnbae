package com.learnbae.my.presentation.screens.updateprofilescreen

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.learnbae.my.R
import com.learnbae.my.data.storage.entities.UpdateUserEntity
import com.learnbae.my.databinding.UpdateProfileLayoutBinding
import com.learnbae.my.domain.datacontracts.model.UserProfileInfoUIModel
import com.learnbae.my.presentation.base.BaseFragment
import com.learnbae.my.presentation.common.showError
import com.learnbae.my.presentation.common.showHelper
import com.learnbae.my.presentation.common.uriToBitmap
import com.learnbae.my.presentation.screens.profilescreen.dialogs.photopickingdialog.PhotoPickingDialog
import dagger.hilt.android.AndroidEntryPoint
import ltst.nibirualert.my.presentation.common.onDestroyNullable

@AndroidEntryPoint
class UpdateProfileFragment : BaseFragment() {
    private var binding by onDestroyNullable<UpdateProfileLayoutBinding>()
    private val viewModel by viewModels<UpdateProfileViewModel>()
    private lateinit var galleryLauncher: ActivityResultLauncher<Intent>
    private lateinit var cameraLauncher: ActivityResultLauncher<Intent>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = UpdateProfileLayoutBinding.inflate(inflater, container, false)
        galleryLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    showProfilePhoto(result.data!!.data!!.uriToBitmap(requireContext()))
                }
            }
        cameraLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val bitmap = result.data!!.extras!!.get("data") as Bitmap
                    showProfilePhoto(bitmap)
                }
            }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setListeners()
        setObservers()
        setNavigationVisibility(false)
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        setNavigationVisibility(true)
        super.onDestroyView()
    }

    private fun setCurrentInfo(userInfo: UserProfileInfoUIModel) {
        binding.apply {
            editTextFullname.editText!!.setText(userInfo.userFullName)
            editTextUsername.editText!!.setText(userInfo.username.substringAfter("@"))
            editTextEmail.editText!!.setText(userInfo.email)
            userInfo.profilePhoto?.let {
                Glide.with(requireContext()).load(it).into(profileImage)
            }
        }
    }

    private fun onAddPhotoButtonClick() {
        PhotoPickingDialog().apply {
            setActionListener(object : PhotoPickingDialog.PickPhotoButtonClickListener {
                override fun onPickCameraClick(intent: Intent) {
                    cameraLauncher.launch(intent)
                }

                override fun onPickGalleryClick(intent: Intent) {
                    galleryLauncher.launch(intent)
                }
            })
        }.show(requireActivity().supportFragmentManager, "PhotoPickingDialogFragmentTag")
    }

    private fun setListeners() {
        binding.apply {
            backButton.setOnClickListener { viewModel.navigateToPreviousScreen() }
            changeButton.setOnClickListener { onAddPhotoButtonClick() }
            saveButton.setOnClickListener {
                viewModel.saveChanges(
                    UpdateUserEntity(
                        editTextUsername.editText?.text.toString(),
                        editTextFullname.editText?.text.toString(),
                        editTextEmail.editText?.text.toString(),
                        null,
                        editTextPassword.editText?.text.toString()
                    )
                )
            }
            logoutButton.setOnClickListener {
                showConfirmActionDialog(
                    resources.getString(R.string.update_screen_log_out_message),
                    resources.getString(R.string.update_screen_log_out_confirm_message),
                    resources.getString(R.string.update_screen_log_out_cancel_message),
                    viewModel::logOut
                )
            }
            editTextEmail.editText?.setOnFocusChangeListener { view, b ->
                viewModel.onEmailEditTextFocus(b)
            }
        }
    }

    private fun showProfilePhoto(photo: Bitmap) {
        Glide.with(requireContext()).load(photo).into(binding.profileImage)
        viewModel.onChangePhotoButtonClick(photo)
    }

    private fun setObservers() {
        binding.apply {
            viewModel.apply {
                userData.observe(viewLifecycleOwner){
                    setCurrentInfo(it)
                }

                usernameError.observe(viewLifecycleOwner) {
                    editTextUsername.showError(it?.let { textId -> resources.getString(textId) }
                        ?: "")
                }

                successDialogText.observe(viewLifecycleOwner) {
                    showInformationDialog(it?.let { textId -> resources.getString(textId) }
                        ?: "")
                }

                emailError.observe(viewLifecycleOwner) {
                    editTextEmail.showError(it?.let { textId -> resources.getString(textId) }
                        ?: "")
                }

                fullNameError.observe(viewLifecycleOwner) {
                    editTextFullname.showError(it?.let { textId -> resources.getString(textId) }
                        ?: "")
                }

                passwordHelperText.observe(viewLifecycleOwner) {
                    editTextPassword.showHelper(it?.let { textId -> resources.getString(textId) }
                        ?: "")
                }

                passwordError.observe(viewLifecycleOwner) {
                    editTextPassword.showError(it?.let { textId -> resources.getString(textId) }
                        ?: "")
                }
            }
        }
    }
}
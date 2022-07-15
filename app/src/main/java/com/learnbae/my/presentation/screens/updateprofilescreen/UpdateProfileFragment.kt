package com.learnbae.my.presentation.screens.updateprofilescreen

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.learnbae.my.R
import com.learnbae.my.data.storage.entities.UpdateUserEntity
import com.learnbae.my.databinding.UpdateProfileLayoutBinding
import com.learnbae.my.domain.datacontracts.model.UserProfileInfoUIModel
import com.learnbae.my.presentation.base.BaseFragment
import com.learnbae.my.presentation.common.showError
import com.learnbae.my.presentation.screens.profilescreen.dialogs.photopickingdialog.PhotoPickingDialog
import dagger.hilt.android.AndroidEntryPoint
import ltst.nibirualert.my.presentation.common.onDestroyNullable

@AndroidEntryPoint
class UpdateProfileFragment : BaseFragment() {
    companion object {
        private const val KEY = "PROFILE INFO"
        fun newInstance(profileInfo: UserProfileInfoUIModel) = UpdateProfileFragment().apply {
            arguments = Bundle().apply {
                putString(KEY, Gson().toJson(profileInfo))
            }
        }
    }

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
                    showProfilePhoto(uri = result.data!!.data!!)
                }
            }
        cameraLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val bitmap = result.data!!.extras!!.get("data") as Bitmap
                    showProfilePhoto(bitmap = bitmap)
                }
            }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setCurrentInfo()
        setListeners()
        setObservers()
        setNavigationVisibility(false)
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        setNavigationVisibility(true)
        super.onDestroyView()
    }

    private fun setCurrentInfo() {
        val userInfo = Gson().fromJson(
            requireArguments().getString(KEY),
            UserProfileInfoUIModel::class.java
        )

        binding.apply {
            textFullName.editText!!.setText(userInfo.userFullName)
            textUsername.editText!!.setText(userInfo.username.substringAfter("@"))
            textEmail.editText!!.setText(userInfo.email)
            userInfo.profilePhoto?.let {
                Glide.with(requireContext()).load(it).into(binding.profileImage)
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
                        textUsername.editText?.text.toString(),
                        textFullName.editText?.text.toString(),
                        textEmail.editText?.text.toString()
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
        }
    }

    private fun showProfilePhoto(uri: Uri? = null, bitmap: Bitmap? = null) {
        Glide.with(requireContext()).load(uri ?: bitmap).into(binding.profileImage)
    }

    private fun setObservers() {
        binding.apply {
            viewModel.apply {
                usernameError.observe(viewLifecycleOwner) {
                    textUsername.showError(it?.let { textId -> resources.getString(textId) }
                        ?: "")
                }
            }
        }
    }
}
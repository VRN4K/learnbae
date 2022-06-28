package com.learnbae.my.presentation.screens.profilescreen

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
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.learnbae.my.databinding.ProfileLayoutBinding
import com.learnbae.my.presentation.common.livedata.StateData
import com.learnbae.my.presentation.screens.profilescreen.photopickingdialog.PhotoPickingDialog
import ltst.nibirualert.my.presentation.common.onDestroyNullable

class ProfileFragment : Fragment() {
    private var binding by onDestroyNullable<ProfileLayoutBinding>()
    private val profileViewModel by lazy { ViewModelProvider(this).get(ProfileViewModel::class.java) }
    private lateinit var galleryLauncher: ActivityResultLauncher<Intent>
    private lateinit var cameraLauncher: ActivityResultLauncher<Intent>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ProfileLayoutBinding.inflate(inflater, container, false)
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
        super.onViewCreated(view, savedInstanceState)
        setListeners()
        setObservers()
    }

    private fun setObservers() {
        profileViewModel.apply {
            userInformation.observe(viewLifecycleOwner) {
                when (it.status) {
                    StateData.DataStatus.LOADING -> showScreenContent(true)
                    StateData.DataStatus.COMPLETE -> showScreenContent(false)
                    else -> return@observe
                }
            }
        }
    }

    private fun showScreenContent(isShow: Boolean) {
        binding.loadingAnimator.changeLoadingState(isShow, binding.screenContent.id)
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

    private fun showProfilePhoto(uri: Uri? = null, bitmap: Bitmap? = null) {
        Glide.with(requireContext()).load(uri ?: bitmap).into(binding.profileImage)
    }

    private fun setListeners() {
        binding.apply {
            profileImage.setOnClickListener { onAddPhotoButtonClick() }
            logoutButton.setOnClickListener { profileViewModel.logout() }
        }
    }
}
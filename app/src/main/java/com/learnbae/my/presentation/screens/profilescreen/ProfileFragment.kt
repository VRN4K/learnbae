package com.learnbae.my.presentation.screens.profilescreen

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.learnbae.my.R
import com.learnbae.my.databinding.ProfileLayoutBinding
import com.learnbae.my.domain.datacontracts.model.UserProfileInfoUIModel
import com.learnbae.my.presentation.base.BaseFragment
import com.learnbae.my.presentation.common.convertProfileBitmapToFile
import com.learnbae.my.presentation.common.livedata.StateData
import com.learnbae.my.presentation.screens.Screens
import com.learnbae.my.presentation.screens.profilescreen.dialogs.englishlevelpickingdialog.LevelPickingDialog
import com.learnbae.my.presentation.screens.profilescreen.dialogs.photopickingdialog.PhotoPickingDialog
import dagger.hilt.android.AndroidEntryPoint
import ltst.nibirualert.my.presentation.common.onDestroyNullable

@AndroidEntryPoint
class ProfileFragment : BaseFragment() {
    private var binding by onDestroyNullable<ProfileLayoutBinding>()
    private val viewModel by viewModels<ProfileViewModel>()
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
                    showProfilePhoto(bitmap.convertProfileBitmapToFile(context?.filesDir?.absolutePath!!))
                }
            }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.isUserAuthorizedCheck()
        showSynchronizeLoading(false)
        setListeners()
        setObservers()
    }

    private fun setListeners() {
        binding.apply {
            profileImage.setOnClickListener { onAddPhotoButtonClick() }
            updateEnglishLevelButton.setOnClickListener { onChangeLevelButtonClick() }
            synchronizeWordsButton.setOnClickListener { viewModel.synchronizeWords() }
            deleteButton.setOnClickListener {
                showConfirmActionDialog(
                    resources.getString(R.string.profile_account_delete_account_message),
                    resources.getString(R.string.profile_account_confirm_delete_account_text),
                    resources.getString(R.string.profile_account_cancel_delete_account_text),
                    viewModel::deleteAccount,
                )
            }
            changePasswordButton.setOnClickListener { viewModel.navigateToScreen(Screens.getChangePasswordScreen()) }
        }
    }

    private fun setObservers() {
        viewModel.apply {
            userInformation.observe(viewLifecycleOwner) {
                when (it.status) {
                    StateData.DataStatus.LOADING -> showScreenContent(true)
                    StateData.DataStatus.COMPLETE -> with(it.data!!) {
                        showProfileInfo(this)
                        showScreenContent(false)
                    }
                    else -> return@observe
                }
            }
            isSynchronizing.observe(viewLifecycleOwner) {
                showSynchronizeLoading(it)
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

    private fun onChangeLevelButtonClick() {
        LevelPickingDialog().apply {
            setActionListener(object : LevelPickingDialog.PickEnglishLevelClickListener {
                override fun onLevelClick(levelValue: String) {
                    binding.englishLevelValue.text = levelValue
                    viewModel.updateEnglishLevel(levelValue)
                }
            })
        }.show(requireActivity().supportFragmentManager, "LevelPickingDialogFragmentTag")
    }

    private fun showSynchronizeLoading(isShow: Boolean) {
        binding.synchronizationLoading.changeLoadingState(isShow, binding.synchronizeWordsButton.id)
    }

    private fun showProfileInfo(info: UserProfileInfoUIModel) {
        binding.apply {
            userName.text = info.username
            englishLevelValue.text = info.englishLevel
            userFullName.text = info.userFullName
            userEmail.text = info.email
            userSingUpDate.text = info.singUpDate
            wordsCount.text = info.wordsCount
            info.profilePhoto?.let {
                Glide.with(requireContext()).load(it).into(binding.profileImage)
            }
            updateProfileButton.setOnClickListener {
                viewModel.navigateToScreen(
                    Screens.getUpdateProfileScreen(
                        info.apply {
                            binding.apply {
                                profilePhoto = Bitmap.createBitmap(
                                    profileImage.drawable.intrinsicWidth,
                                    profileImage.drawable.intrinsicHeight,
                                    Bitmap.Config.ARGB_8888
                                ).convertProfileBitmapToFile(
                                    context?.filesDir?.absolutePath!!
                                ).toString()
                            }
                        }
                    )
                )
            }
        }
    }

    private fun showProfilePhoto(uri: Uri) {
        viewModel.addUserProfilePhoto(uri)
        Glide.with(requireContext()).load(uri).into(binding.profileImage)
    }
}
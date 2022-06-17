package com.learnbae.my.presentation.screens.profilescreen

import android.app.Activity
import android.content.Intent
import android.content.Intent.ACTION_PICK
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.learnbae.my.databinding.ProfileLayoutBinding
import com.learnbae.my.domain.datacontracts.model.ActionType
import com.learnbae.my.domain.datacontracts.model.IntentPhotoPickDataModel
import com.learnbae.my.presentation.screens.profilescreen.photopickingdialog.PhotoPickingDialog
import ltst.nibirualert.my.presentation.common.onDestroyNullable

class ProfileFragment : Fragment() {
    private var binding by onDestroyNullable<ProfileLayoutBinding>()
    private lateinit var launcher: ActivityResultLauncher<Intent>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ProfileLayoutBinding.inflate(inflater, container, false)
        launcher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val selectedPhoto: Uri =
                        result.data!!.data!!
                    Glide.with(requireContext()).load(selectedPhoto).into(binding.profileImage)
                }
            }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.profileImage.setOnClickListener { onAddPhotoButtonClick() }
    }

    private fun onAddPhotoButtonClick() {
        PhotoPickingDialog().apply {
            setActionListener(object : PhotoPickingDialog.PickPhotoButtonClickListener {
                override fun onPickPhotoClick(actionType: ActionType) {
                    requireContext().startActivity(
                        Intent(
                            ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        ).apply {
                            putExtra(
                                PhotoPickingDialog.DATA_KEY, Gson().toJson(
                                    IntentPhotoPickDataModel(
                                        actionType,
                                        binding.profileImage
                                    )
                                )
                            )
                        }
                    )
                }
            })
        }.show(requireActivity().supportFragmentManager, "PhotoPickingDialogFragmentTag")
    }
}
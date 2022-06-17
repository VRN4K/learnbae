package com.learnbae.my.presentation.screens.profilescreen.photopickingdialog

import android.content.Intent
import android.content.Intent.EXTRA_TEXT
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.google.gson.Gson
import com.learnbae.my.databinding.PhotoPickingDialogBinding
import com.learnbae.my.domain.datacontracts.model.ActionType
import ltst.nibirualert.my.presentation.common.onDestroyNullable

class PhotoPickingDialog : DialogFragment() {
    private var binding by onDestroyNullable<PhotoPickingDialogBinding>()
    private var listener: PickPhotoButtonClickListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = PhotoPickingDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog!!.window!!.attributes.width = ViewGroup.LayoutParams.MATCH_PARENT
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setListeners()
    }

    private fun setListeners() {
        binding.apply {
            pickFromGalleryButton.setOnClickListener {
                listener!!.onPickGalleryClick(
                    Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                )
                dialog!!.dismiss()
            }

            pickFromCameraButton.setOnClickListener {
                listener!!.onPickCameraClick(Intent(MediaStore.ACTION_IMAGE_CAPTURE))
                dialog!!.dismiss()
            }
        }
    }

    fun setActionListener(listener: PickPhotoButtonClickListener) {
        this.listener = listener
    }

    interface PickPhotoButtonClickListener {
        fun onPickGalleryClick(intent: Intent) {}
        fun onPickCameraClick(intent: Intent) {}
    }
}
package com.learnbae.my.presentation.screens.profilescreen.photopickingdialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.learnbae.my.databinding.PhotoPickingDialogBinding
import com.learnbae.my.domain.datacontracts.model.ActionType
import ltst.nibirualert.my.presentation.common.onDestroyNullable

class PhotoPickingDialog : DialogFragment() {
    companion object {
        const val DATA_KEY = "Intent data key"
    }
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
                listener!!.onPickPhotoClick(ActionType.LOAD_IMAGE_GALLERY)
            }

            pickFromCameraButton.setOnClickListener {
                listener!!.onPickPhotoClick(ActionType.LOAD_IMAGE_CAMERA)
            }

//            requireContext().startActivity(
//                Intent(
//                    ACTION_PICK,
//                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
//                ).apply {
//                    putExtra(
//                        DATA_KEY, Gson().toJson(
//                            IntentDataModel(
//                                ActionType.LOAD_IMAGE_CAMERA
//                            )
//                        )
//                    )
//                }
//            )
        }
    }

    fun setActionListener(listener: PickPhotoButtonClickListener) {
        this.listener = listener
    }

    interface PickPhotoButtonClickListener {
        fun onPickPhotoClick(actionType: ActionType) {}
    }
}
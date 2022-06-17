package com.learnbae.my.domain.datacontracts.model

import android.view.View

class IntentPhotoPickDataModel(
    val actionType: ActionType,
    val viewPhoto: View
)

enum class ActionType {
    LOAD_IMAGE_GALLERY, LOAD_IMAGE_CAMERA
}
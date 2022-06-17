package com.learnbae.my.presentation.common

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.widget.ImageView
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContentProviderCompat.requireContext
import com.bumptech.glide.Glide
import com.github.terrakok.cicerone.Router
import com.google.android.exoplayer2.source.MediaSource
import com.google.gson.Gson
import com.learnbae.my.domain.datacontracts.model.ActionType
import com.learnbae.my.domain.datacontracts.model.IntentPhotoPickDataModel
import com.learnbae.my.presentation.screens.profilescreen.photopickingdialog.PhotoPickingDialog.Companion.DATA_KEY
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.core.component.KoinComponent
import kotlin.coroutines.CoroutineContext

class ActionLauncher(private val context: Context) : KoinComponent, CoroutineScope {
    override val coroutineContext: CoroutineContext = Dispatchers.Main + SupervisorJob()

    fun launch(intent: Intent) {
        when (intent.type) {
            Intent.ACTION_PICK, MediaStore.ACTION_IMAGE_CAPTURE -> workWithPhotoPickingIntent(intent)
        }
    }

    private fun workWithPhotoPickingIntent(intent: Intent) {
        val intentData = Gson().fromJson(intent.getStringExtra(DATA_KEY), IntentPhotoPickDataModel::class.java)
        val selectedPhoto: Uri = intent.data!!
        Glide.with(context).load(selectedPhoto).into(intentData.viewPhoto as ImageView)
    }
}
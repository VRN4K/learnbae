package com.learnbae.my.presentation.common

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.core.content.FileProvider
import androidx.core.graphics.drawable.toBitmap
import coil.ImageLoader
import coil.request.ImageRequest
import com.bumptech.glide.Glide
import com.google.android.material.textfield.TextInputLayout
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

fun TextInputLayout.showError(errorText: String = "") {
    this.error = errorText
}

fun TextInputLayout.showHelper(helperText: String = "") {
    this.helperText = helperText
}

fun View.setVisibility(isVisible: Boolean = true) {
    this.visibility = if (isVisible) VISIBLE else GONE
}

fun Any?.toEmptyOnNullString() = this?.toString() ?: ""

fun Bitmap.convertProfileBitmapToFile(
    appAbsolutePath: String,
    context: android.content.Context
): Uri {
    val fileDirectoryPath = "${appAbsolutePath}/images"

    val directory = File(fileDirectoryPath).also {
        if (!it.exists()) it.mkdirs()
    }

    val image = File(directory, "profile.png")
    image.createNewFile()
    val out = FileOutputStream(image)

    this.compress(Bitmap.CompressFormat.PNG, 100, out)
    out.flush()
    out.close()

    return FileProvider.getUriForFile(
        context,
        "com.learnbae.my.fileprovider",
        File(image.absolutePath)
    )
}

fun Uri.uriToBitmap(context: android.content.Context): Bitmap {
    return compressBitmap(MediaStore.Images.Media.getBitmap(context.contentResolver, this), 100)
}

fun compressBitmap(bitmap: Bitmap, quality: Int): Bitmap {
    val stream = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.PNG, quality, stream)
    val byteArray = stream.toByteArray()
    return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
}
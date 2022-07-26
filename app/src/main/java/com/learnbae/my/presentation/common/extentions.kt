package com.learnbae.my.presentation.common

import android.graphics.Bitmap
import android.net.Uri
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import com.google.android.material.textfield.TextInputLayout
import java.io.File
import java.io.FileOutputStream

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

fun Bitmap.convertProfileBitmapToFile(appAbsolutePath: String): Uri {
    val fileDirectoryPath = "${appAbsolutePath}/images"
    println(fileDirectoryPath)
    val directory = File(fileDirectoryPath).also {
        if (!it.exists()) it.mkdirs()
    }

    val image = File(directory, "profile.png")
    image.createNewFile()
    val out = FileOutputStream(image)

    this.compress(Bitmap.CompressFormat.PNG, 100, out)
    out.flush()
    out.close()

    return Uri.fromFile(File(image.absolutePath))
}
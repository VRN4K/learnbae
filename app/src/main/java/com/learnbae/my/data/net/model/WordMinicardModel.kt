package com.learnbae.my.data.net.model

import android.content.res.Resources
import com.google.gson.annotations.SerializedName
import com.learnbae.my.R
import com.learnbae.my.domain.datacontracts.model.WordMinicardUI
import com.learnbae.my.presentation.common.extensions.splitTranslation

data class WordMinicardModel(
    @SerializedName("Translation")
    val translation: Translation1
)

data class Translation1(
    @SerializedName("Heading")
    val heading: String,
    @SerializedName("Translation")
    val translation: String
)

fun WordMinicardModel.toUI(
    resources: Resources,
    translation: TranslationModel,
    partOfSpeech: String,
    soundURL: String?
): WordMinicardUI {
    return WordMinicardUI(
        this.translation.heading.replaceFirstChar { it.uppercase() },
        partOfSpeech,
        resources.getString(
            R.string.transcription_pattern,
            translation.Body.first().Markup.first().Text
        ),
        this.translation.translation.splitTranslation(),
        soundURL
    )
}

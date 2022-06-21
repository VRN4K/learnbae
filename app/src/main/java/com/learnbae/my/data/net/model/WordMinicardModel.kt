package com.learnbae.my.data.net.model

import android.content.res.Resources
import com.google.gson.annotations.SerializedName
import com.learnbae.my.R
import com.learnbae.my.domain.datacontracts.model.WordMinicardUI
import com.learnbae.my.presentation.common.extensions.splitTranslation

data class WordMinicardModel(
    @SerializedName("Translation")
    val translation: Translation
)

data class Translation(
    @SerializedName("Heading")
    val heading: String,
    @SerializedName("DictionaryName")
    val dictionaryName: String,
    @SerializedName("Translation")
    val translation: String,
    @SerializedName("SoundName")
    val soundName: String
)

fun WordMinicardModel.toUI(resources: Resources, translation: TranslationModel): WordMinicardUI {
    return WordMinicardUI(
        this.translation.heading.replaceFirstChar { it.uppercase() },
        this.translation.dictionaryName,
        resources.getString(
            R.string.transcription_pattern,
            translation.Body.first().Markup.first().Text
        ),
        this.translation.translation.splitTranslation(),
        this.translation.soundName
    )
}

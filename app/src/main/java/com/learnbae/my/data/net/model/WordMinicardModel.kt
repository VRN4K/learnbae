package com.learnbae.my.data.net.model

import com.google.gson.annotations.SerializedName

data class WordMinicardModel(
    @SerializedName("Translation")
    val translation: Translation
)

data class Translation(
    @SerializedName("Heading")
    val heading: String,
    @SerializedName("Translation")
    val translation: String,
    @SerializedName("SoundName")
    val soundName: String
)


package com.learnbae.my.data.net.model

import com.google.gson.annotations.SerializedName

class WordOfADayModel(
    @SerializedName("word")
    val word: String,
    @SerializedName("definitions")
    val definitions: List<Definition>,
)

data class Definition (
    @SerializedName("partOfSpeech")
    val partOfSpeech: String
)

data class AudioModel(
    @SerializedName("fileUrl")
    val fileURL: String? = null
)

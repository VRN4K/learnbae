package com.learnbae.my.domain.datacontracts.model

class WordMinicardUI(
    val title: String,
    val partOfSpeech: String,
    val transcription: String,
    val translation: List<String>,
    val soundURL: String? = null
)

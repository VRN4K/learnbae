package com.learnbae.my.data.storage.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.learnbae.my.domain.datacontracts.model.VocabularyWordUI
import java.util.*

@Entity(tableName = "vocabulary")
class WordEntity(
    @PrimaryKey
    val id: String,
    val word: String,
    val transcription: String,
    val translation: String
)

fun VocabularyWordUI.toEntity(): WordEntity {
    return WordEntity(
        UUID.randomUUID().toString(),
        this.title,
        this.transcription,
        this.translation
    )
}

fun WordEntity.toUI(): VocabularyWordUI {
    return VocabularyWordUI(
        this.id,
        this.word,
        this.transcription,
        this.translation
    )
}
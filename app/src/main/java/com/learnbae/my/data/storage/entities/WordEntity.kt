package com.learnbae.my.data.storage.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.learnbae.my.domain.datacontracts.model.VocabularyWordUI

@Entity(tableName = "vocabulary")
class WordEntity(
    @PrimaryKey
    val id: String,
    val word: String,
    val translation: String
)

class FireBaseWordEntity(
    val word: String,
    val translation: String
)

fun VocabularyWordUI.toEntity(): WordEntity {
    return WordEntity(
        this.id,
        this.title,
        this.translation
    )
}

fun WordEntity.toUI(): VocabularyWordUI {
    return VocabularyWordUI(
        this.id,
        this.word,
        this.translation
    )
}

fun VocabularyWordUI.toFareBaseEntity(): FireBaseWordEntity {
    return FireBaseWordEntity(
        this.title,
        this.translation
    )
}

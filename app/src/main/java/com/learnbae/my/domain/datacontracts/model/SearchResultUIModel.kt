package com.learnbae.my.domain.datacontracts.model

import com.learnbae.my.data.net.model.SearchResultModel

class SearchResultUIModel(
    val word: String,
    val pronunciations: String,
    val wordSounds: MutableMap<String, String>,
    val translation: List<String>,
    val examples: MutableMap<String, List<String>>

)

fun SearchResultModel.toUI(): SearchResultUIModel {
    return SearchResultUIModel(
        this.word.replaceFirstChar { it.uppercase() },
        this.results.first().lexicalEntries.first().entries.first().pronunciations.first().phoneticSpelling,
        mutableMapOf<String, String>().apply {
            results.first().lexicalEntries.first().entries.first().pronunciations.onEach {
                put(it.dialects.first(), it.audioFile)
            }
        },
        this.results.first().lexicalEntries.first().entries.first().senses.first().translations.map { it.text },
        mutableMapOf<String, List<String>>().apply {
            results.first().lexicalEntries.first().entries.first().senses.first().examples.onEach { example ->
                put(
                    example.text,
                    example.translations.map { translation -> translation.text })
            }
        }
    )
}
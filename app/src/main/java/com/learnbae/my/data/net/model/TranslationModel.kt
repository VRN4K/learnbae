package com.learnbae.my.data.net.model

import com.google.gson.annotations.SerializedName

data class SearchResultModel(
    @SerializedName("word")
    val word: String,
    @SerializedName("results")
    val results: List<Result>
)

data class Result(
    @SerializedName("lexicalEntries")
    val lexicalEntries: List<LexicalEntry>
)

data class LexicalEntry(
    @SerializedName("entries")
    val entries: List<Entry>,
    @SerializedName("lexicalCategory")
    val lexicalCategory: LexicalCategory
)

data class LexicalCategory(
    @SerializedName("text")
    val text: String
)

data class Entry(
    @SerializedName("pronunciations")
    val pronunciations: List<Pronunciation>?,
    @SerializedName("senses")
    val senses: List<Sense>
)

data class Pronunciation(
    @SerializedName("audioFile")
    val audioFile: String,
    @SerializedName("dialects")
    val dialects: List<String>,
    @SerializedName("phoneticSpelling")
    val phoneticSpelling: String
)

data class Example(
    @SerializedName("text")
    val text: String,
    @SerializedName("translations")
    val translations: List<Translation>
)

data class Sense(
    @SerializedName("examples")
    val examples: List<Example>?,
    @SerializedName("translations")
    val translations: List<Translation>
)

data class Translation(
    @SerializedName("language")
    val language: String,
    @SerializedName("text")
    val text: String
)


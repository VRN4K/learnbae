package com.learnbae.my.data.model

import com.google.gson.annotations.SerializedName

class TranslationModel(
    @SerializedName("Title"       ) var Title       : String?                = null,
    @SerializedName("TitleMarkup" ) var TitleMarkup : ArrayList<TitleMarkup> = arrayListOf(),
    @SerializedName("Dictionary"  ) var Dictionary  : String?                = null,
    @SerializedName("ArticleId"   ) var ArticleId   : String?                = null,
    @SerializedName("Body"        ) var Body        : ArrayList<Body>        = arrayListOf()
)

data class TitleMarkup (

    @SerializedName("IsItalics"  ) var IsItalics  : Boolean? = null,
    @SerializedName("IsAccent"   ) var IsAccent   : Boolean? = null,
    @SerializedName("Node"       ) var Node       : String?  = null,
    @SerializedName("Text"       ) var Text       : String?  = null,
    @SerializedName("IsOptional" ) var IsOptional : Boolean? = null

)

data class Markup (

    @SerializedName("Node"       ) var Node       : String?  = null,
    @SerializedName("Text"       ) var Text       : String?  = null,
    @SerializedName("IsOptional" ) var IsOptional : Boolean? = null

)

data class Body (

    @SerializedName("Markup"     ) var Markup     : ArrayList<Markup> = arrayListOf(),
    @SerializedName("Node"       ) var Node       : String?           = null,
    @SerializedName("Text"       ) var Text       : String?           = null,
    @SerializedName("IsOptional" ) var IsOptional : Boolean?          = null

)
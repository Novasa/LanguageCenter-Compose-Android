package com.novasa.languagecenter.languagecenterliabary_features.domain.api_models

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable
import java.sql.Timestamp

@Serializable
data class StringModel(
    val key: String = "",
    val value: String = "",
    val language: String = "",
    val timestamp: Long = 0,
    @SerializedName("html_tags")
    val htmlTags: Array<String> = arrayOf()
)

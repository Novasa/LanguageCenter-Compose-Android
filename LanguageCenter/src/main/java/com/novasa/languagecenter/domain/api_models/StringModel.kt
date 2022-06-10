package com.novasa.languagecenter.domain.api_models

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class StringModel(
    val key: String = "",
    val value: String = "",
    val language: String = "",
    val timestamp: Long = 0,
    @SerializedName("html_tags")
    val htmlTags: List<String> = emptyList()
)

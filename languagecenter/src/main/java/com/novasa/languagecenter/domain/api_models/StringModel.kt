package com.novasa.languagecenter.domain.api_models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StringModel(
    val key: String = "",
    val value: String = "",
    val language: String = "",
    val timestamp: Long = 0,
    @SerialName("html_tags")
    val htmlTags: List<String> = emptyList()
)

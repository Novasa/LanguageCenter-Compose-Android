package com.novasa.languagecenter.domain.api_models
import kotlinx.serialization.Serializable

@Serializable
data class PostStringModel (
    val platform: String = "android",
    val category: String,
    val key: String,
    val value: String = "test: no value",
)

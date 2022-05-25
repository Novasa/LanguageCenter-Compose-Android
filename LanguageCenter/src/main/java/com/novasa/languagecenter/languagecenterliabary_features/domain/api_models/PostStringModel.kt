package com.novasa.languagecenter.languagecenterliabary_features.domain.api_models
import kotlinx.serialization.Serializable

@Serializable
data class PostStringModel (
    val category: String,
    val key: String,
    val value: String,
)

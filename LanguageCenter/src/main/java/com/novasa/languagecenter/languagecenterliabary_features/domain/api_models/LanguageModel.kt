package com.novasa.languagecenter.languagecenterliabary_features.domain.api_models

import kotlinx.serialization.Serializable

@Serializable
data class LanguageModel(
    val name: String,
    val codename: String,
    val is_fallback: Boolean,
    val timestamp: Int,
)

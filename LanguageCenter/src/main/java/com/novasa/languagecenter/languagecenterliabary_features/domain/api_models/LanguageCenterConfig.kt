package com.novasa.languagecenter.languagecenterliabary_features.domain.api_models

import kotlinx.serialization.Serializable

@Serializable
data class LanguageCenterConfig(
    val baseUrl: String,
    val userName: String,
    val password: String,
)

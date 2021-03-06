package com.novasa.languagecenter.domain.api_models

import kotlinx.serialization.Serializable

@Serializable
data class LanguageCenterConfig(
    val baseUrl: String,
    val password: String,
    val userName: String,
)

package com.novasa.languagecenter.languagecenterliabary_features.domain.api_models

import kotlinx.serialization.Serializable

@Serializable
data class StringModel (
    val key: String,
    val value: String,
    val language: String,
)



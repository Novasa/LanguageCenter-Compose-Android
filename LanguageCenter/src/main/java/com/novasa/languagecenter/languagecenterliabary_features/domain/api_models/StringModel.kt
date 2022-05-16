package com.example.languagecenterliabary.languagecenterliabary_features.domain.api_models

import com.google.gson.annotations.SerializedName

data class StringModel (
    val key: StringSubModel,
)

data class StringSubModel (
    val key: String,
    val value: String,
    val language: String,
)

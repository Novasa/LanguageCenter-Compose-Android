package com.example.languagecenterliabary.languagecenterliabary_features.domain.model
import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class ApiModel(
    val id: Int,
    val image: String,
    val text: String
)

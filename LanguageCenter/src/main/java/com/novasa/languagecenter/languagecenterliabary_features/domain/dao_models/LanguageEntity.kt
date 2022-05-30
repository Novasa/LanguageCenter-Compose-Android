package com.novasa.languagecenter.languagecenterliabary_features.domain.dao_models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "LanguageEntity")
data class LanguageEntity(
    val name: String,
    @PrimaryKey
    val codename: String,
    val is_fallback: Boolean,
    val timestamp: Long
)

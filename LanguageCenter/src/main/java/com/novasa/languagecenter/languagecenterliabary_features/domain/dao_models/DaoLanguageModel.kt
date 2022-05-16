package com.novasa.languagecenter.languagecenterliabary_features.domain.dao_models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "LanguageEntity")
data class DaoLanguageModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val key: String,
    val value: String,
    val language: String,
)

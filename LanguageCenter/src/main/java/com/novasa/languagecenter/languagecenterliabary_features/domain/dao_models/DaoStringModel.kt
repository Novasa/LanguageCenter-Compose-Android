package com.novasa.languagecenter.languagecenterliabary_features.domain.dao_models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "TranslationsEntity")
data class DaoStringModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val key: String,
    val value: String,
    val language: String,
)



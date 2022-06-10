package com.novasa.languagecenter.domain.dao_models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "TranslationsEntity",
    foreignKeys = [
        ForeignKey(
            entity = LanguageEntity::class,
            parentColumns = ["codename"],
            childColumns = ["language"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index("language")
    ]
)

data class TranslationEntity(
    @PrimaryKey
    val key: String,
    val value: String,
    val language: String,
)

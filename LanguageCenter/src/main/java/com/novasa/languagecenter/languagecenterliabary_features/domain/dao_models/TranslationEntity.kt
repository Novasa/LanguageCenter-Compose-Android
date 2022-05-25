package com.novasa.languagecenter.languagecenterliabary_features.domain.dao_models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.novasa.languagecenter.languagecenterliabary_features.domain.api_models.AccountInfoModel

@Entity(tableName = "TranslationsEntity", foreignKeys = [
        ForeignKey(
            entity = LanguageEntity::class,
            parentColumns = ["codename"],
            childColumns = ["language"],
            onDelete = ForeignKey.CASCADE
        )
])

data class TranslationEntity(
    @PrimaryKey
    val key: String?,
    val value: String?,
    val language: String?,
    val htmlTags: List<AccountInfoModel> = listOf()
)



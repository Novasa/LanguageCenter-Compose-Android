package com.example.languagecenterliabary.languagecenterliabary_features.domain

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todoTable")
data class DaoModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val image: String,
    val text: String
)



package com.novasa.languagecenter.languagecenterliabary_features.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.novasa.languagecenter.languagecenterliabary_features.domain.dao_models.LanguageEntity
import com.novasa.languagecenter.languagecenterliabary_features.domain.dao_models.TranslationEntity

@Database(entities = [TranslationEntity::class, LanguageEntity::class], version = 9, exportSchema = false)
abstract class DaoDatabase : RoomDatabase(){

    abstract fun getDao(): DaoFunctions
}
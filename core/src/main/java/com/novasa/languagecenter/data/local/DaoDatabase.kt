package com.novasa.languagecenter.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.novasa.languagecenter.domain.dao_models.LanguageEntity
import com.novasa.languagecenter.domain.dao_models.TranslationEntity

@Database(entities = [TranslationEntity::class, LanguageEntity::class], version = 1, exportSchema = false)
abstract class DaoDatabase : RoomDatabase() {

    abstract fun getDao(): DaoFunctions

}

package com.novasa.languagecenter.data.local

import androidx.room.*
import com.novasa.languagecenter.domain.dao_models.LanguageEntity
import com.novasa.languagecenter.domain.dao_models.TranslationEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DaoFunctions {

    @Transaction
    suspend fun deleteDbInsertTranslationsAndLanguage(
        languageEntity: LanguageEntity,
        translationEntity: List<TranslationEntity>,
    ) {
        insertLanguage(languageEntity)
        insertTranslations(translationEntity)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTranslations(translationEntity: List<TranslationEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTranslation(translationEntity: TranslationEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLanguage(languageEntity: LanguageEntity)

    @Query("select * from TranslationsEntity")
    fun getTranslations(): Flow<List<TranslationEntity>>

    @Query("select timestamp from LanguageEntity where codename is :language")
    fun getLanguageInfo(language: String): Long

    @Query("delete from LanguageEntity where codename is :language")
    suspend fun deleteItem(language: String)

    @Update
    suspend fun updateItem(translationEntity: TranslationEntity)
}
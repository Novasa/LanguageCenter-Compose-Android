package com.novasa.languagecenter.languagecenterliabary_features.data.local

import androidx.room.*
import com.novasa.languagecenter.languagecenterliabary_features.domain.dao_models.LanguageEntity
import com.novasa.languagecenter.languagecenterliabary_features.domain.dao_models.TranslationEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DaoFunctions {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTranslations(translationEntity: List<TranslationEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTranslation(translationEntity: TranslationEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLanguage(languageEntity: LanguageEntity)

    @Query("select * from TranslationsEntity")
    fun getTranslations(): Flow<List<TranslationEntity>>

    @Query("select * from LanguageEntity")
    fun getLanguageInfo(): LanguageEntity

    @Delete
    suspend fun deleteItem(translationEntity: List<TranslationEntity>)

    @Update
    suspend fun updateItem(translationEntity: TranslationEntity)
}
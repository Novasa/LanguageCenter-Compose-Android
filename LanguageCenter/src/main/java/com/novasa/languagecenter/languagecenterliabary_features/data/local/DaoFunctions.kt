package com.novasa.languagecenter.languagecenterliabary_features.data.local

import androidx.room.*
import com.novasa.languagecenter.languagecenterliabary_features.domain.dao_models.TranslationEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DaoFunctions {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(TranslationEntity: TranslationEntity)

    @Query("select * from TranslationsEntity")
    fun getAllItems(): Flow<List<TranslationEntity>>

    @Delete
    suspend fun deleteItem(TranslationEntity: kotlinx.coroutines.flow.Flow<kotlin.collections.List<com.novasa.languagecenter.languagecenterliabary_features.domain.dao_models.TranslationEntity>>)

    @Update
    suspend fun updateItem(TranslationEntity: TranslationEntity)
}
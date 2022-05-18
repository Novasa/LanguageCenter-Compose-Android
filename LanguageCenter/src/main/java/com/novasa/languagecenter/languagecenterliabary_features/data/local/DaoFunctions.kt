package com.novasa.languagecenter.languagecenterliabary_features.data.local

import androidx.room.*
import com.novasa.languagecenter.languagecenterliabary_features.domain.dao_models.DaoStringModel
import kotlinx.coroutines.flow.Flow

@Dao
interface DaoFunctions {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(daoStringModel: DaoStringModel)

    @Query("select * from TranslationsEntity")
    fun getAllItems(): List<DaoStringModel>

    @Delete
    suspend fun deleteItem(daoStringModel: List<DaoStringModel>)

    @Update
    suspend fun updateItem(daoStringModel: DaoStringModel)
}
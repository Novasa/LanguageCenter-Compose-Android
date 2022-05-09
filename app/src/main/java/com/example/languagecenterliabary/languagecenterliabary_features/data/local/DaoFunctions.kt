package com.example.languagecenterliabary.languagecenterliabary_features.data.local

import androidx.room.*
import com.example.languagecenterliabary.languagecenterliabary_features.domain.DaoModel
import kotlinx.coroutines.flow.Flow

@Dao
interface DaoFunctions {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(daoModel: DaoModel)

    @Query("select * from todoTable")
    fun getAllItems(): Flow<List<DaoModel>>

    @Delete
    suspend fun deleteItem(daoModel: DaoModel)

    @Update
    suspend fun updateItem(daoModel: DaoModel)
}
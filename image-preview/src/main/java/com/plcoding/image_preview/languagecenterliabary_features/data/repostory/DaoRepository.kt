package com.example.languagecenterliabary.languagecenterliabary_features.data.repostory

import com.example.languagecenterliabary.languagecenterliabary_features.data.local.DaoFunctions
import com.example.languagecenterliabary.languagecenterliabary_features.domain.DaoModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

class DaoRepository @Inject constructor(private val daoFunctions: DaoFunctions) {

    suspend fun insert(daoModel: DaoModel) = withContext(Dispatchers.IO){
        daoFunctions.insert(daoModel)
    }

    fun getAllItems(): Flow<List<DaoModel>> = daoFunctions.getAllItems()

    suspend fun deleteItem(daoModel: DaoModel) = daoFunctions.deleteItem(daoModel)

    suspend fun updateItem(daoModel: DaoModel) = daoFunctions.updateItem(daoModel)
}
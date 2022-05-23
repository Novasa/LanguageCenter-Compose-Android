package com.novasa.languagecenter.languagecenterliabary_features.data.repostory

import com.novasa.languagecenter.languagecenterliabary_features.data.local.DaoFunctions
import com.novasa.languagecenter.languagecenterliabary_features.domain.dao_models.TranslationEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DaoRepository @Inject constructor(private val daoFunctions: DaoFunctions) {

    suspend fun insert(TranslationEntity: TranslationEntity) = withContext(Dispatchers.IO){
        daoFunctions.insert(TranslationEntity)
    }

    fun getAllItems(): Flow<List<TranslationEntity>> = daoFunctions.getAllItems()

    suspend fun deleteItem(TranslationEntity: Flow<List<TranslationEntity>>) = daoFunctions.deleteItem(TranslationEntity)

    suspend fun updateItem(TranslationEntity: TranslationEntity) = daoFunctions.updateItem(TranslationEntity)
}
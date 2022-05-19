package com.novasa.languagecenter.languagecenterliabary_features.data.repostory

import com.novasa.languagecenter.languagecenterliabary_features.data.local.DaoFunctions
import com.novasa.languagecenter.languagecenterliabary_features.domain.dao_models.DaoStringModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DaoRepository @Inject constructor(private val daoFunctions: DaoFunctions) {

    suspend fun insert(daoStringModel: DaoStringModel) = withContext(Dispatchers.IO){
        daoFunctions.insert(daoStringModel)
    }

    fun getAllItems(): List<DaoStringModel> = daoFunctions.getAllItems()

    suspend fun deleteItem(daoStringModel: List<DaoStringModel>) = daoFunctions.deleteItem(daoStringModel)

    suspend fun updateItem(daoStringModel: DaoStringModel) = daoFunctions.updateItem(daoStringModel)
}
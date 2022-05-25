package com.novasa.languagecenter.languagecenterliabary_features.data.repostory

import com.novasa.languagecenter.languagecenterliabary_features.data.local.DaoFunctions
import com.novasa.languagecenter.languagecenterliabary_features.domain.dao_models.LanguageEntity
import com.novasa.languagecenter.languagecenterliabary_features.domain.dao_models.TranslationEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DaoRepository @Inject constructor(private val daoFunctions: DaoFunctions) {

    suspend fun insertTranslations(translationEntity: List<TranslationEntity>) = withContext(Dispatchers.IO){
        daoFunctions.insertTranslations(translationEntity)
    }

    suspend fun insertTranslation(translationEntity: TranslationEntity) = withContext(Dispatchers.IO){
        daoFunctions.insertTranslation(translationEntity)
    }

    suspend fun insertLanguage(languageEntity: LanguageEntity) = withContext(Dispatchers.IO){
        daoFunctions.insertLanguage(languageEntity)
    }

    fun getTranslations(): Flow<List<TranslationEntity>> = daoFunctions.getTranslations()

    fun getLanguageInfo(): LanguageEntity = daoFunctions.getLanguageInfo()

    suspend fun deleteItem(translationEntity: List<TranslationEntity>) = withContext(Dispatchers.IO){
        daoFunctions.deleteItem(translationEntity)
    }
}
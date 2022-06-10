package com.novasa.languagecenter.data.repostory

import com.novasa.languagecenter.data.local.DaoFunctions
import com.novasa.languagecenter.domain.dao_models.LanguageEntity
import com.novasa.languagecenter.domain.dao_models.TranslationEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DaoRepository @Inject constructor(private val daoFunctions: DaoFunctions) {

    suspend fun updateDaoDb(
        translationEntity: List<TranslationEntity>,
        languageEntity: LanguageEntity
    ) = withContext(Dispatchers.IO) {
        daoFunctions.deleteDbInsertTranslationsAndLanguage(
            languageEntity = languageEntity,
            translationEntity = translationEntity,
        )
    }

    suspend fun insertTranslations(translationEntity: List<TranslationEntity>) = withContext(Dispatchers.IO) {
        daoFunctions.insertTranslations(translationEntity)
    }

    suspend fun insertTranslation(translationEntity: TranslationEntity) = withContext(Dispatchers.IO) {
        daoFunctions.insertTranslation(translationEntity)
    }

    suspend fun insertLanguage(languageEntity: LanguageEntity) = withContext(Dispatchers.IO) {
        daoFunctions.insertLanguage(languageEntity)
    }

    fun getTranslations(): Flow<List<TranslationEntity>> = daoFunctions.getTranslations()

    suspend fun getLanguageInfo(Language: String): LanguageEntity? = withContext(Dispatchers.IO) {
        daoFunctions.getLanguageInfo(Language)
    }

    suspend fun deleteItem(LangCodename: String) = withContext(Dispatchers.IO) {
        daoFunctions.deleteItem(LangCodename)
    }

    suspend fun doesTranslationExist(category: String, key: String) = withContext(Dispatchers.IO) {
        daoFunctions.getTranslation("$category.$key") != null
    }
}


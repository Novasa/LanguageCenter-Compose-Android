package com.novasa.languagecenter.data.repostory

import com.novasa.languagecenter.data.remote.Api
import com.novasa.languagecenter.domain.api_models.LanguageModel
import com.novasa.languagecenter.domain.api_models.PostStringModel
import com.novasa.languagecenter.domain.api_models.StringModel
import javax.inject.Inject


class ApiRepository @Inject constructor(
    private val api: Api
) {
    suspend fun postString (
        category: String,
        key: String,
        value: String,
    ) {
        api.postString(PostStringModel( "android", category, key, value))
    }

    suspend fun getListLanguage(): List<LanguageModel> {
        return api.listLanguages()
    }

    suspend fun getSpecificLanguage(language: String): LanguageModel {
        return api.specificLanguage(language = language)
    }

    suspend fun getListStrings(
        timestamp: String = "on",
        language: String
    ): List<StringModel> {
        return api.listStrings(timestamp, language)
    }
}

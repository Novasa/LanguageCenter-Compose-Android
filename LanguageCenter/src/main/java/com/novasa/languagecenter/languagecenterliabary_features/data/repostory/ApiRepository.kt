package com.novasa.languagecenter.languagecenterliabary_features.data.repostory

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.novasa.languagecenter.languagecenterliabary_features.data.remote.Api
import com.novasa.languagecenter.languagecenterliabary_features.domain.api_models.LanguageModel
import com.novasa.languagecenter.languagecenterliabary_features.domain.api_models.PostStringModel
import com.novasa.languagecenter.languagecenterliabary_features.domain.api_models.StringModel
import com.novasa.languagecenter.languagecenterliabary_features.domain.dao_models.TranslationEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject


class ApiRepository @Inject constructor(
    private val api: Api
) {
    fun postString (
        category: String,
        key: String,
        value: String,
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                api.postString(PostStringModel(category, key, value))
            } catch (e: IOException) {
                Log.d("MainActivity", "$e")
            }
        }
    }

    suspend fun getListLanguage(): List<LanguageModel> {
        return api.listLanguages()
    }

    suspend fun getSpecificLanguage(language: String): LanguageModel {
        return api.specificLanguage(language = language)
    }

    suspend fun getListStrings(language: String): List<StringModel> {
        return api.listStrings("off", language)
    }
}
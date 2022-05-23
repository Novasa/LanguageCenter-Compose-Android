package com.novasa.languagecenter.languagecenterliabary_features.data.repostory

import android.util.Log
import com.novasa.languagecenter.languagecenterliabary_features.data.remote.Api
import com.novasa.languagecenter.languagecenterliabary_features.domain.api_models.LanguageModel
import com.novasa.languagecenter.languagecenterliabary_features.domain.api_models.PostStringModel
import com.novasa.languagecenter.languagecenterliabary_features.domain.api_models.StringModel
import com.novasa.languagecenter.languagecenterliabary_features.use_cases.ConfigureLanguage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

class ApiRepository @Inject constructor(
    private val api: Api,
    private val provider: ConfigureLanguage
) {
    fun postString (
        platform: String,
        category: String,
        key: String,
        value: String,
        comment: String,
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                api.postString(PostStringModel(platform, category, key, value, comment))
            } catch (e: IOException) {
                Log.d("MainActivity", "$e")
            }
        }
    }

    suspend fun getListStrings(): List<StringModel> {

        return api.listStrings(provider.currentLanguage)
    }

     suspend fun getListLanguages(): List<LanguageModel> {
         return api.listLanguages()
    }
}
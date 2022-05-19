package com.novasa.languagecenter.languagecenterliabary_features.data.repostory

import android.util.Log
import com.novasa.languagecenter.languagecenterliabary_features.data.remote.Api
import com.novasa.languagecenter.languagecenterliabary_features.domain.api_models.PostStringModel
import com.novasa.languagecenter.languagecenterliabary_features.domain.dao_models.DaoStringModel
import com.novasa.languagecenter.languagecenterliabary_features.use_cases.ConfigureLanguage
import com.novasa.languagecenter.languagecenterliabary_features.use_cases.ConfigureLanguage.Companion.currentLanguage
import com.novasa.languagecenter.languagecenterliabary_features.use_cases.UnixConverter.getDateTime
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

class ApiRepository @Inject constructor(
    private val api: Api
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

    fun getListStrings(
        dao: DaoRepository,
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val daoTranslations = dao.getAllItems()
                var localTimestamp = 10
                var remoteTimestamp = 0
                if (daoTranslations.isNotEmpty()) {
                    localTimestamp = getDateTime(daoTranslations[daoTranslations.size - 1].timestamp)
                    remoteTimestamp = getDateTime(api.specificLanguage().timestamp)
                }
                // husk at vend pilen om, inden udgivelse!!!!!!!!!!
                if (remoteTimestamp <= localTimestamp) {
                    ConfigureLanguage().LanguageConfiguring(api.listLanguages())
                    val data = api.listStrings(currentLanguage)
                    var forInLoopIndex = 0
                    for (item in data) {
                        forInLoopIndex++
                        dao.insert(
                            DaoStringModel(
                                id = forInLoopIndex,
                                key = item.key,
                                value = item.value,
                                language = "en",
                                timestamp = item.timestamp
                            )
                        )
                    }
                }
            } catch (e: IOException) {
                Log.d("MainActivity", "$e")
            }
        }
    }
}
package com.novasa.languagecenter.languagecenterliabary_features.data.repostory

import android.util.Log
import androidx.compose.runtime.collectAsState
import com.novasa.languagecenter.languagecenterliabary_features.data.remote.Api
import com.novasa.languagecenter.languagecenterliabary_features.domain.api_models.PostStringModel
import com.novasa.languagecenter.languagecenterliabary_features.domain.dao_models.DaoStringModel
import com.novasa.languagecenter.languagecenterliabary_features.presentation.LCViewModel
import com.novasa.languagecenter.languagecenterliabary_features.use_cases.UnixConverter.getDateTime
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.io.IOException
import java.sql.Date
import java.text.SimpleDateFormat
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
                val data = api.postString(PostStringModel(platform, category, key, value, comment))
                data
                Log.d("dataaaaaaaa", "${data}")
            } catch (e: IOException) {
                Log.d("MainActivity", "${e}")
            }
        }
    }

    fun getSpecificLanguage() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val data = api.specificLanguage()
                data
                Log.d("dataaaaaaaa", "${data}")
            } catch (e: IOException) {
                Log.d("MainActivity", "${e}")
            }
        }
    }

    fun getListLanguages(

    ) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val data = api.listLanguages()
                data
                Log.d("dataaaaaaaa", "${data}")
            } catch (e: IOException) {
                Log.d("MainActivity", "${e}")
            }
        }
    }
    fun getListStrings(
        dao: DaoRepository,
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val localTimestamp = getDateTime(dao.getAllItems()[2].timestamp)
                val remoteTimestamp = getDateTime(api.specificLanguage().timestamp)
                if (remoteTimestamp!! >= localTimestamp!!) {
                    val data = api.listStrings()
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
                Log.d("MainActivity", "${e}")
            }
        }
    }

    fun getAccountInfo() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val data = api.accountInfo()
                data
                Log.d("dataaaaaaaa", "${data}")
            } catch (e: IOException) {
                Log.d("MainActivity", "${e}")
            }
        }
    }
}
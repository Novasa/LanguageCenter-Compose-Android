package com.novasa.languagecenter.languagecenterliabary_features.data.repostory

import android.util.Log
import com.novasa.languagecenter.languagecenterliabary_features.data.remote.Api
import com.novasa.languagecenter.languagecenterliabary_features.domain.api_models.PostStringModel
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
                val data = api.postString(PostStringModel(platform, category, key, value, comment))
                data
                Log.d("dataaaaaaaa", "${data}")
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
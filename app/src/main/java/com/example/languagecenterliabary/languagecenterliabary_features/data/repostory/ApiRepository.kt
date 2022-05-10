package com.example.languagecenterliabary.languagecenterliabary_features.data.repostory

import android.util.Log
import com.example.languagecenterliabary.languagecenterliabary_features.data.remote.Api
import com.example.languagecenterliabary.languagecenterliabary_features.domain.DaoModel
import com.example.languagecenterliabary.languagecenterliabary_features.domain.api_models.PostStringModel
import com.example.languagecenterliabary.languagecenterliabary_features.presentation.MainViewModel
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
}
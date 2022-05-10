package com.example.languagecenterliabary.languagecenterliabary_features.data.repostory

import android.util.Log
import com.example.languagecenterliabary.languagecenterliabary_features.data.remote.Api
import com.example.languagecenterliabary.languagecenterliabary_features.data.remote.Api.Companion.BASE_URL
import com.example.languagecenterliabary.languagecenterliabary_features.domain.DaoModel
import com.example.languagecenterliabary.languagecenterliabary_features.domain.model.ApiModel
import com.example.languagecenterliabary.languagecenterliabary_features.presentation.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class ApiRepository @Inject constructor(
    api: Api
) {
    val api = api
    fun GetData(
        state: MainViewModel
    ) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val data = api.getMemes()
                state.insert(DaoModel(account_name = data.account_name))
                Log.d("dataaaaaaaa", "${api.getMemes()}")
            } catch (e: IOException) {
                Log.d("MainActivity", "${e}")
            }
        }
    }
}
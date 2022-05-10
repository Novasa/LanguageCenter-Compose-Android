package com.example.languagecenterliabary.languagecenterliabary_features.data.remote


import com.example.languagecenterliabary.languagecenterliabary_features.domain.api_models.AccountInfoModel
import com.example.languagecenterliabary.languagecenterliabary_features.domain.api_models.LanguageModel
import com.example.languagecenterliabary.languagecenterliabary_features.domain.api_models.PostStringModel
import com.example.languagecenterliabary.languagecenterliabary_features.domain.api_models.StringModel
import okhttp3.ResponseBody
import retrofit2.http.*

interface Api {

    @GET("info")
    suspend fun accountInfo(): AccountInfoModel

    @GET("language/da")
    suspend fun specificLanguage(): LanguageModel

    @GET("languages")
    suspend fun listLanguages(): List<LanguageModel>

    @GET("strings?platform=:android")
    suspend fun listStrings(): List<StringModel>

    @POST("string")
    suspend fun postString(@Body requestBody: PostStringModel): ResponseBody

    companion object {
        const val BASE_URL = "https://language.novasa.com/sorgen/api/v1/"
    }
}
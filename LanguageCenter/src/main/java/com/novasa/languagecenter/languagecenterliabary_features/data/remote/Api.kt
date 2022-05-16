package com.novasa.languagecenter.languagecenterliabary_features.data.remote


import com.novasa.languagecenter.languagecenterliabary_features.domain.api_models.AccountInfoModel
import com.novasa.languagecenter.languagecenterliabary_features.domain.api_models.LanguageModel
import com.novasa.languagecenter.languagecenterliabary_features.domain.api_models.PostStringModel
import com.novasa.languagecenter.languagecenterliabary_features.domain.api_models.StringModel
import okhttp3.ResponseBody
import retrofit2.http.*

interface Api {

    @GET("info")
    suspend fun accountInfo(): AccountInfoModel

    @GET("language/da")
    suspend fun specificLanguage(): LanguageModel

    @GET("languages")
    suspend fun listLanguages(): List<LanguageModel>

    @GET("strings?platform=android&language=da&indexing=off&timestamp=off&html=off")
    suspend fun listStrings(): List<StringModel>

    @POST("string")
    suspend fun postString(@Body requestBody: PostStringModel): ResponseBody

    companion object {
        const val BASE_URL = "https://language.novasa.com/sorgen/api/v1/"
    }
}
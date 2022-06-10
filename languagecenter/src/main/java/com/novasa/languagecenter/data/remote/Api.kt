package com.novasa.languagecenter.data.remote


import com.novasa.languagecenter.domain.api_models.AccountInfoModel
import com.novasa.languagecenter.domain.api_models.LanguageModel
import com.novasa.languagecenter.domain.api_models.PostStringModel
import com.novasa.languagecenter.domain.api_models.StringModel
import okhttp3.ResponseBody
import retrofit2.http.*

interface Api {

    @GET("v1/info")
    suspend fun accountInfo(): AccountInfoModel

    @GET("v1/language/{language}?timestamp=on")
    suspend fun specificLanguage(@Path("language") language: String): LanguageModel

    @GET("v1/languages?timestamp=on")
    suspend fun listLanguages(): List<LanguageModel>

    @GET("v1/strings?platform=android&indexing=off")
    suspend fun listStrings(
        @Query("timestamp") timestamp: String,
        @Query("language") language: String
    ): List<StringModel>

    @POST("v1/string")
    suspend fun postString(@Body requestBody: PostStringModel): ResponseBody

}

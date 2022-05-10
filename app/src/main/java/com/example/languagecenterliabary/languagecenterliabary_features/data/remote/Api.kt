package com.example.languagecenterliabary.languagecenterliabary_features.data.remote


import com.example.languagecenterliabary.languagecenterliabary_features.domain.model.ApiModel
import retrofit2.http.*

interface Api {
    @GET("/sorgen/api/v1/info")
    suspend fun getMemes(): ApiModel

    companion object {
        const val BASE_URL = "https://language.novasa.com"
    }
}
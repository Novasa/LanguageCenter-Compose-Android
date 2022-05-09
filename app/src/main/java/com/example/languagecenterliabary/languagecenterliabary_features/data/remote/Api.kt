package com.example.languagecenterliabary.languagecenterliabary_features.data.remote


import com.example.languagecenterliabary.languagecenterliabary_features.domain.model.ApiModel
import retrofit2.http.*

interface Api {
    @GET("user")
    suspend fun getMemes(): List<ApiModel>

    companion object {
        const val BASE_URL = "https://us-central1-rest-api-ed3bc.cloudfunctions.net"
    //"https://language.novasa.com"
    }
}
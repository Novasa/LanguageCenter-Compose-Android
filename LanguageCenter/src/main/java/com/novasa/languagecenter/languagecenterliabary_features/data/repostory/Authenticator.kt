package com.novasa.languagecenter.languagecenterliabary_features.data.repostory

import android.content.Context
import kotlinx.coroutines.runBlocking
import okhttp3.*

class Auth(
    username: String,
    password: String
) : Authenticator {

    val credential = Credentials.basic(username = username, password = password);

    override fun authenticate(route: Route?, response: Response): Request? {
        return response.request
            .newBuilder()
            .header("Authorization", credential)
            .build()
    }
}
package com.novasa.languagecenter.data.repostory

import okhttp3.*

class Auth(
    username: String,
    password: String
) : Authenticator {

    private val credential = Credentials.basic(username = username, password = password);

    override fun authenticate(route: Route?, response: Response): Request {
        return response.request
            .newBuilder()
            .header("Authorization", credential)
            .build()
    }
}

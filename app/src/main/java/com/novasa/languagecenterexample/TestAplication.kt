package com.novasa.languagecenterexample

import android.app.Application
import com.novasa.languagecenter.languagecenterliabary_features.domain.api_models.LanguageCenterConfig
import com.novasa.languagecenter.languagecenterliabary_features.provider.LanguageCenterProvider
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class TestAplication: Application() {
    @Inject
    lateinit var provider: LanguageCenterProvider

    override fun onCreate() {
        super.onCreate()
        provider.configure(
            config = LanguageCenterConfig(
                baseUrl = "https://language.novasa.com/sorgen/api/v1/",
                userName = "kagekage",
                password = "novasa"
            )
        )
    }
}
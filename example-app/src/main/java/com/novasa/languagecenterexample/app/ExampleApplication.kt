package com.novasa.languagecenterexample.app

import android.app.Application
import com.novasa.languagecenter.domain.api_models.LanguageCenterConfig
import com.novasa.languagecenter.provider.LCProvider
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class ExampleApplication : Application() {

    @Inject
    lateinit var lcProvider: LCProvider

    override fun onCreate() {
        super.onCreate()

        lcProvider.configure(LanguageCenterConfig(
                baseUrl = "https://language.novasa.com/test/api/",
                password = "test",
                userName = "test"
        ))
    }
}

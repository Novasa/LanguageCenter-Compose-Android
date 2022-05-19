package com.novasa.languagecenterexample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.novasa.languagecenter.languagecenterliabary_features.domain.api_models.LanguageCenterConfig
import com.novasa.languagecenter.languagecenterliabary_features.presentation.LCViewModel
import com.novasa.languagecenter.languagecenterliabary_features.presentation.TranslatedText
import com.novasa.languagecenterexample.ui.theme.AndroidLibraryTheme
import com.novasa.languagecenter.languagecenterliabary_features.provider.LCContextProviderImpl
import com.novasa.languagecenter.languagecenterliabary_features.provider.LCLanguageProviderImpl
import com.novasa.languagecenter.languagecenterliabary_features.provider.LCProviderImpl
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: LCViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LCProviderImpl().configure(
            config = LanguageCenterConfig(
                baseUrl = "https://language.novasa.com/sorgen/api/v1/",
                password = "kagekage",
                userName = "novasa",
            )
        )
        LCContextProviderImpl().setContext(this)
        LCLanguageProviderImpl().setLanguage("en")
        setContent {
            val Translations = viewModel.getListStrings().collectAsState().value
            AndroidLibraryTheme {
                Box(modifier = Modifier.fillMaxSize()) {
                    Column() {
                        Button(
                            onClick = {
                                viewModel.postString(
                                    platform = "android",
                                    category = "LC test3",
                                    key = "LC_test3",
                                    value = "second test from LC libary",
                                    comment = "this is just a test"
                                )
                            }
                        ) {
                            Text(text = "upload string")
                        }
                        Button(
                            onClick = { viewModel.deleteDaoTranslations() }
                        ) {
                            Text(text = "delete dao")
                        }
                        TranslatedText(
                            LanguageCenterViewModel = viewModel,
                            TextKey = "test.test",
                        )
                        if (Translations.isNotEmpty()) {
                            Text(text = Translations.toString())
                        }
                    }
                }
            }
        }
    }
}

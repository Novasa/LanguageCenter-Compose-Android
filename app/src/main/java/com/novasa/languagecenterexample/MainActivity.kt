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
import com.novasa.languagecenter.languagecenterliabary_features.provider.LCProviderImpl
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: LCViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.provider.configure(
            config = LanguageCenterConfig (
                baseUrl = "https://language.novasa.com/sorgen/api/v1/",
                password = "kagekage",
                userName = "novasa",
            )
        )
        viewModel.provider.setLanguage("da")
        viewModel.getListStrings()
        setContent {
            val Translations = viewModel.response.collectAsState().value
            val status = viewModel.currentStatus.collectAsState().value
            AndroidLibraryTheme {
                Box(modifier = Modifier.fillMaxSize()) {
                    Column() {
                        Button(
                            onClick = {
                                viewModel.postString (
                                    platform = "android",
                                    category = "LC test",
                                    key = "LC_test4",
                                    value = "second test from LC libary",
                                    html = "@" //* to enable
                                )
                            }
                        ) {
                            Text(text = "upload string")
                        }

                        Text(text = status.toString())
                        TranslatedText(
                            LanguageCenterViewModel = viewModel,
                            TextKey = "test.test",
                        )
                        if (status == LCViewModel.Status.READY) {
                            Text(text = Translations.toString())
                        }
                    }
                }
            }
        }
    }
}

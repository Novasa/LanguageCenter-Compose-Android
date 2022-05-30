package com.novasa.languagecenterexample

import android.os.Bundle
import android.util.Log
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
import com.novasa.languagecenter.languagecenterliabary_features.domain.api_models.PostStringModel
import com.novasa.languagecenter.languagecenterliabary_features.domain.dao_models.TranslationEntity
import com.novasa.languagecenter.languagecenterliabary_features.presentation.LCViewModel
import com.novasa.languagecenter.languagecenterliabary_features.presentation.translatedText
import com.novasa.languagecenterexample.ui.theme.AndroidLibraryTheme
import com.novasa.languagecenter.languagecenterliabary_features.provider.LCProviderImpl
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: LCViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.provider.setLanguage("da")
        viewModel.getListStrings()
        setContent {
           Text(text = translatedText(key = "", fallback = ""))

            val initialValue = TranslationEntity("","","")
            val text = viewModel.translations.collectAsState().value
            AndroidLibraryTheme {
                Box(modifier = Modifier.fillMaxSize()) {
                    Column() {
                        Button(
                            onClick = {
                                viewModel.test()
                            }
                        ) {
                            Text(text = translatedText(viewModel, "test.test", "hello world" ))
                        }
                    }
                }
            }
        }
    }
}

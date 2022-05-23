package com.novasa.languagecenterexample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.novasa.languagecenter.languagecenterliabary_features.domain.api_models.LanguageCenterConfig
import com.novasa.languagecenter.languagecenterliabary_features.presentation.LCViewModel
import com.novasa.languagecenter.languagecenterliabary_features.presentation.TranslatedText
import com.novasa.languagecenterexample.ui.theme.AndroidLibraryTheme
import com.novasa.languagecenter.languagecenterliabary_features.provider.LCProviderImpl
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import java.sql.Date
import java.text.SimpleDateFormat

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: LCViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.provider.configure(
            config = LanguageCenterConfig(
                baseUrl = "https://language.novasa.com/sorgen/api/v1/",
                password = "kagekage",
                userName = "novasa",
            )
        )
        viewModel.provider.language = "en"
        setContent {
            val translations = viewModel.getListStrings().collectAsState().value
            AndroidLibraryTheme {
                Box(modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp)
                ) {
                    Text(text = "$translations")
                }
            }
        }
    }
}

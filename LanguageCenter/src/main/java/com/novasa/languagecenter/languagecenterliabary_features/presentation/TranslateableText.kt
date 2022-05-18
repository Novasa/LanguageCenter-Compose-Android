package com.novasa.languagecenter.languagecenterliabary_features.presentation

import android.util.Log
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.novasa.languagecenter.languagecenterliabary_features.domain.dao_models.DaoStringModel
import com.novasa.languagecenter.languagecenterliabary_features.provider.LanguageCenterProvierImpl
import kotlinx.coroutines.flow.conflate

@Composable
fun TranslatedText(
    LanguageCenterViewModel: LCViewModel = viewModel(),
    TextKey: String
) {
    LanguageCenterViewModel.getListStrings()
    val translations = LanguageCenterViewModel.response.collectAsState().value
    var displayText = ""
    for (text in translations) {
        if (text.key == TextKey) {
            displayText = text.value
        }
    }
    Text(text = displayText)
}


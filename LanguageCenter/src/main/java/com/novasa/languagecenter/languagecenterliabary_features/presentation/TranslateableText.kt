package com.novasa.languagecenter.languagecenterliabary_features.presentation

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.text.TextStyle
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun TranslatedText(
    LanguageCenterViewModel: LCViewModel = viewModel(),
    TextKey: String,
    textStyle: TextStyle = TextStyle()
) {
    LanguageCenterViewModel.getListStrings()
    val translations = LanguageCenterViewModel.response.collectAsState().value
    var displayText = ""
    for (text in translations) {
        if (text.key == TextKey) {
            displayText = text.value
        }
    }
    Text(
        text = displayText,
        style = textStyle
    )
}


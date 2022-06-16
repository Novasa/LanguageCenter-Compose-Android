package com.novasa.languagecenter.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun translatedText(
    viewModel: LCViewModel = hiltViewModel(),
    category: String,
    key: String,
    fallback: String,
    enable_html: String = ""
): String {

    LaunchedEffect(category, key) {
        viewModel.ensureTranslationExist(
            category = category,
            key = key,
            value = fallback,
            html = enable_html
        )
    }

    val translations = viewModel.translations.collectAsState().value

    return translations["$category.$key"]?.value ?: fallback
}

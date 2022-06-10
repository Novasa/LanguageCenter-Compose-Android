package com.novasa.languagecenter.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun translatedText(
    viewModel: LCViewModel = viewModel(),
    category: String,
    key: String,
    fallback: String,
): String {

    LaunchedEffect(category, key) {
        viewModel.ensureTranslationExist(
            category = category,
            key = key,
            value = fallback
        )
    }

    val translations = viewModel.translations.collectAsState().value

    return translations["$category.$key"]?.value ?: fallback
}

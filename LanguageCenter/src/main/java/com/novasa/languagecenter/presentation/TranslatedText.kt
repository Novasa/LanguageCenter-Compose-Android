package com.novasa.languagecenter.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState

@Composable
fun translatedText(
    viewModel: LCViewModel,
    key: String,
    fallback: String,
): String {

    viewModel.ensureTranslationExist(
        key = key,
        value = fallback,
        language = "da"
    )

    val translations = viewModel.translations.collectAsState().value

    return translations[key]?.value ?: fallback
}

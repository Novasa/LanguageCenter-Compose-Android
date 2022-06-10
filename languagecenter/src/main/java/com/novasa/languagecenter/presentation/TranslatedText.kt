package com.novasa.languagecenter.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun translatedText(
    category: String,
    key: String,
    fallback: String,
): String = translatedText(key = "$category.$key", fallback = fallback)

@Composable
fun translatedText(
    viewModel: LCViewModel = viewModel(),
    key: String,
    fallback: String,
): String {

    viewModel.ensureTranslationExist(
        key = key,
        value = fallback
    )

    val translations = viewModel.translations.collectAsState().value

    return translations[key]?.value ?: fallback
}

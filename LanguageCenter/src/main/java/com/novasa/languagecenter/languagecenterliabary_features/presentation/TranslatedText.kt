package com.novasa.languagecenter.languagecenterliabary_features.presentation

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.novasa.languagecenter.languagecenterliabary_features.domain.api_models.PostStringModel

@Composable
fun translatedText(
    viewModel: LCViewModel = viewModel(),
    key: String,
    fallback: String,
) :String {

    viewModel.ensureTranslationExist(
        key = key,
        value = fallback,
        category = "da"
    )
    val translations = viewModel.translations.collectAsState().value

    return translations[key]?.value ?: fallback
}


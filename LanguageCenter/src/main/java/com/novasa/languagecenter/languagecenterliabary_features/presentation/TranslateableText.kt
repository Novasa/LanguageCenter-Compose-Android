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
fun TranslatedText(
    LanguageCenterViewModel: LCViewModel = viewModel(),
    TranslationData: PostStringModel,
    textStyle: TextStyle = TextStyle(
        fontSize = 20.sp,
        color = Color.Cyan,
    )
) {
    LanguageCenterViewModel.getListStrings()
    val displayText = LanguageCenterViewModel.getReponse(
        value = TranslationData.value,
        key = TranslationData.key,
        category = TranslationData.category
    ).collectAsState().value

    Text(
        text = displayText!!.value,
        style = TextStyle()
    )
}


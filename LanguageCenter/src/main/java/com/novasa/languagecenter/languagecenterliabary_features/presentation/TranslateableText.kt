package com.novasa.languagecenter.languagecenterliabary_features.presentation

import android.util.Log
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel


@Composable
fun TreanslateableText(
    LanguageCenterViewModel: LCViewModel = viewModel()
    //key
) {
    val data = LanguageCenterViewModel.response.collectAsState().value
    var testText = ""
    Button(
        onClick = {
            LanguageCenterViewModel.getListStrings()
            Log.d("dataaa", "${LanguageCenterViewModel.getListStrings()}")
        }
    ) {
        Text(text = "${LanguageCenterViewModel.response.collectAsState().value}")
    }
}


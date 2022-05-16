package com.novasa.languagecenter.languagecenterliabary_features.presentation

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun TreanslateableText(
    viewmodel: LCViewModel = viewModel()
    //key
) {
    Text(text = "hello")
}


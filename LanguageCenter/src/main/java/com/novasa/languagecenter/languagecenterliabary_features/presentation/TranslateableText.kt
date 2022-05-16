package com.novasa.languagecenter.languagecenterliabary_features.presentation

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.languagecenterliabary.languagecenterliabary_features.presentation.LCViewModel

@Composable
fun TreanslateableText(
    viewmodel: LCViewModel = viewModel()
    //key
) {
    Text(text = "hello")
}


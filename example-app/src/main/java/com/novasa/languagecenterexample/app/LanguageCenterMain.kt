package com.novasa.languagecenterexample.app

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.novasa.languagecenter.presentation.LCViewModel
import com.novasa.languagecenter.presentation.translatedText
import com.novasa.languagecenterexample.R

const val LC_CATEGORY = "languagecenter_test"

@Composable
fun LanguageCenterMain(
    viewModel: LCViewModel = viewModel()
) {
    val status = viewModel.status.collectAsState()



    Scaffold(topBar = {
        TopAppBar(title = {
            Text(text = stringResource(id = R.string.app_name))
        })
    }) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Status: ${status.value}")

            Text(text = translatedText(category = LC_CATEGORY, key = "basic", fallback = "Languagecenter is not initialized!"))
        }
    }

    LaunchedEffect(Unit) {
        viewModel.initialize()
    }
}

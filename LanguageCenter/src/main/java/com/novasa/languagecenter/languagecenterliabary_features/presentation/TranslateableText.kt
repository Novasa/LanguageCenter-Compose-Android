package com.novasa.languagecenter.languagecenterliabary_features.presentation

import android.util.Log
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.novasa.languagecenter.languagecenterliabary_features.domain.dao_models.DaoStringModel
import com.novasa.languagecenter.languagecenterliabary_features.provider.LanguageCenterProvierImpl
import kotlinx.coroutines.flow.conflate

@Composable
fun TreanslateableText(
    LanguageCenterViewModel: LCViewModel = viewModel()
    //key
) {
    val xx = LanguageCenterViewModel.response.collectAsState().value
    Button(onClick = {
        LanguageCenterViewModel.getListStrings()
    }) {
        Text(text = xx.toString())
    }
}


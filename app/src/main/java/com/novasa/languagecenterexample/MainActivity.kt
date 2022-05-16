package com.novasa.languagecenterexample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.novasa.languagecenter.languagecenterliabary_features.presentation.LCViewModel
import com.novasa.languagecenterexample.ui.theme.AndroidLibraryTheme
import com.novasa.languagecenter.languagecenterliabary_features.presentation.TreanslateableText
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: LCViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidLibraryTheme {
                Box(modifier = Modifier.fillMaxSize()) {
                    TreanslateableText(viewModel)
                }
            }
        }
    }
}

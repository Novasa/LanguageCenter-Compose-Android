package com.plcoding.androidlibrary

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.ui.Modifier
import com.example.languagecenterliabary.MainActivity
import com.plcoding.androidlibrary.ui.theme.AndroidLibraryTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidLibraryTheme {
                Box(modifier = Modifier.fillMaxSize()) {
                    Button(
                        onClick = {
                            MainActivity().get()
                        }
                    ) {
                    }
                }
            }
        }
    }
}

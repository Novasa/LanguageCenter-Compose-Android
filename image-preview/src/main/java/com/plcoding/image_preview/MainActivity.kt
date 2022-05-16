package com.example.languagecenterliabary

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.languagecenterliabary.languagecenterliabary_features.domain.DaoModel
import com.example.languagecenterliabary.languagecenterliabary_features.presentation.MainViewModel
import com.example.languagecenterliabary.languagecenterliabary_features.presentation.theme.ComposeRoomTheme
import com.plcoding.image_preview.DaoDatabase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.runBlocking

class LanguageCenter() {
    fun get(context: Context) {
        runBlocking {
            val db = DaoDatabase.getDatabase(context).getDao().insert(DaoModel(account_name = "whip_machine_lokild"))
            Log.d("dataaaaaaaaaaaaa", "$db")
        }
    }
}
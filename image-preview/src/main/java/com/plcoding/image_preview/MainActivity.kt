package com.example.languagecenterliabary

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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.languagecenterliabary.languagecenterliabary_features.data.repostory.ApiRepository
import com.example.languagecenterliabary.languagecenterliabary_features.data.repostory.DaoRepository
import com.example.languagecenterliabary.languagecenterliabary_features.di.ApiModule
import com.example.languagecenterliabary.languagecenterliabary_features.di.DaoModule
import com.example.languagecenterliabary.languagecenterliabary_features.domain.DaoModel
import com.example.languagecenterliabary.languagecenterliabary_features.presentation.MainViewModel
import com.example.languagecenterliabary.languagecenterliabary_features.presentation.theme.ComposeRoomTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val todoViewModel: MainViewModel by viewModels()

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val data = todoViewModel.getListStrings(todoViewModel)
        setContent {
            ComposeRoomTheme {
                Surface(color = MaterialTheme.colors.background) {
                    AddToolbar()
                }
            }
        }
    }

    fun get() {
        ApiRepository(ApiModule.api()).getAccountInfo()
    }


    //disse koder skal fjernes, nar koder skal udgives
    @Composable
    fun AddToolbar() {
        val responseData = todoViewModel.response.collectAsState().value
        Scaffold(topBar = {
            TopAppBar(title = {
                Text(text = "Dao Items")
            })
        },
            floatingActionButton = {
                val openDialog = remember{ mutableStateOf(false) }
                FloatingActionButton(onClick = {
                    Log.d("helloooo", "${responseData}")
                    todoViewModel.getListLanguages(todoViewModel)
                    todoViewModel.getSpecificLanguage(todoViewModel)
                    todoViewModel.getListStrings(todoViewModel)
                //todo.title = title.value
                    //todo.description = description.value
                    //todoViewModel.update(todo)
                    //todoViewModel.delete(todo)
                }) {
                    Icon(Icons.Default.Add, contentDescription = "Add", tint = Color.White)
                }
            }
        ) {
            RecyclerView(viewModel = todoViewModel)
        }
    }

    fun insert(account_name: MutableState<String>){
        if (!TextUtils.isEmpty(account_name.value)){
            todoViewModel.insert(DaoModel(account_name = account_name.value,))
            Toast.makeText(this@MainActivity, "Inserted...", Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(this@MainActivity, "Empty...", Toast.LENGTH_SHORT).show()
        }
    }

    private fun update(title: MutableState<String>, description: MutableState<String>, daoModel: DaoModel){
        if (!TextUtils.isEmpty(title.value) && !TextUtils.isEmpty(description.value)){
            todoViewModel.update(daoModel)
            Toast.makeText(this@MainActivity, "Updated...", Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(this@MainActivity, "Empty...", Toast.LENGTH_SHORT).show()
        }
    }

    @Composable
    fun RecyclerView(viewModel: MainViewModel) {
        val value = viewModel.response.collectAsState()
        LazyColumn(modifier = Modifier.padding(vertical = 10.dp)) {
            items(value.value){ todo ->
                TodoItem(daoModel = todo)
            }
        }
    }

    @Composable
    fun TodoItem(daoModel: DaoModel) {
        val openUpdateDialog = remember{ mutableStateOf(false) }
        Card(
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 5.dp)
                .fillMaxWidth(),
            elevation = 4.dp,
            shape = RoundedCornerShape(4.dp)
        ) {
            val openDeleteDialog = remember{ mutableStateOf(false) }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.padding(10.dp)) {
                    Text(text = daoModel.account_name, fontWeight = FontWeight.ExtraBold)
                }
                IconButton(onClick = {
                    openDeleteDialog.value = true
                }) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = "delete")
                    DeleteDialogBox(openDeleteDialog = openDeleteDialog, daoModel)
                }
            }
        }
    }

    @Composable
    fun DeleteDialogBox(openDeleteDialog: MutableState<Boolean>, daoModel: DaoModel) {
        TextButton(onClick = {
            todoViewModel.delete(daoModel)
            openDeleteDialog.value = false
        }) {
        }
    }
}


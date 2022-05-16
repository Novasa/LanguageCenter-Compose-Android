package com.novasa.languagecenter.languagecenterliabary_features.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.novasa.languagecenter.languagecenterliabary_features.data.remote.Api
import com.novasa.languagecenter.languagecenterliabary_features.domain.dao_models.DaoStringModel
import com.novasa.languagecenter.languagecenterliabary_features.data.repostory.DaoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class LCViewModel @Inject constructor(
    private val daoRepository: DaoRepository,
    private val api: Api
): ViewModel() {
    //for dao
    val _state = MutableStateFlow<List<DaoStringModel>>(emptyList())

    val response: StateFlow<List<DaoStringModel>>
        get() = _state

    fun insert(daoStringModel: DaoStringModel) = viewModelScope.launch {
        daoRepository.insert(daoStringModel)
    }

    fun delete(daoStringModel: DaoStringModel) = viewModelScope.launch {
        daoRepository.deleteItem(daoStringModel)
    }

    fun update(daoStringModel: DaoStringModel) = viewModelScope.launch {
        daoRepository.updateItem(daoStringModel)
    }

    //for apis
    fun getAccountInfo() {
        viewModelScope.launch {
            try {
                val data = api.accountInfo()
                data
                Log.d("dataaaaaaaa", "${data}")
            } catch (e: IOException) {
                Log.d("MainActivity", "${e}")
            }
        }
    }
    fun getSpecificLanguage() {
        viewModelScope.launch {
            try {
                val data = api.specificLanguage()
                data
                Log.d("dataaaaaaaa", "${data}")
            } catch (e: IOException) {
                Log.d("MainActivity", "${e}")
            }
        }
    }

    fun getListLanguages() {
        viewModelScope.launch {
            try {
                val data = api.listLanguages()
                data
                Log.d("dataaaaaaaa", "${data}")
            } catch (e: IOException) {
                Log.d("MainActivity", "${e}")
            }
        }
    }
    fun getListStrings() {
        viewModelScope.launch {
            try {
                val data = api.accountInfo()
                data
                Log.d("dataaaaaaaa", "${data}")
            } catch (e: IOException) {
                Log.d("MainActivity", "${e}")
            }
        }
    }

    init {
        getAllTodos()
    }

    fun getAllTodos() = viewModelScope.launch {
        daoRepository.getAllItems()
            .catch { e->
                Log.d("main", "Exception: ${e.message}")
            }.collect {
                _state.value = it
            }
    }
}
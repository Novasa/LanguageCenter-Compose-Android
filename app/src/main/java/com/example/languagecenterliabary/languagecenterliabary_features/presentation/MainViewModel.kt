package com.example.languagecenterliabary.languagecenterliabary_features.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.languagecenterliabary.languagecenterliabary_features.data.remote.Api
import com.example.languagecenterliabary.languagecenterliabary_features.data.repostory.ApiRepository
import com.example.languagecenterliabary.languagecenterliabary_features.domain.DaoModel
import com.example.languagecenterliabary.languagecenterliabary_features.data.repostory.DaoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val daoRepository: DaoRepository,
    private val api: Api
): ViewModel() {
    val _state = MutableStateFlow<List<DaoModel>>(emptyList())

    val response: StateFlow<List<DaoModel>>
        get() = _state

    fun insert(daoModel: DaoModel) = viewModelScope.launch {
        daoRepository.insert(daoModel)
    }

    fun delete(daoModel: DaoModel) = viewModelScope.launch {
        daoRepository.deleteItem(daoModel)
    }

    fun update(daoModel: DaoModel) = viewModelScope.launch {
        daoRepository.updateItem(daoModel)
    }

    fun getData(api: MainViewModel) = viewModelScope.launch {
        ApiRepository(api.api).GetData(api)
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
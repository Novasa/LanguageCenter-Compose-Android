package com.novasa.languagecenter.languagecenterliabary_features.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.novasa.languagecenter.languagecenterliabary_features.data.local.DaoDatabase
import com.novasa.languagecenter.languagecenterliabary_features.data.remote.Api
import com.novasa.languagecenter.languagecenterliabary_features.data.repostory.ApiRepository
import com.novasa.languagecenter.languagecenterliabary_features.domain.dao_models.DaoStringModel
import com.novasa.languagecenter.languagecenterliabary_features.data.repostory.DaoRepository
import com.novasa.languagecenter.languagecenterliabary_features.use_cases.hasInternet
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class LCViewModel @Inject constructor(
    private val daoRepository: DaoRepository,
    private val api: ApiRepository
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
                val data = api.getAccountInfo()
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
                val data = api.getSpecificLanguage()
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
                val data = daoRepository.getAllItems()
                val qq = daoRepository.insert(
                    daoStringModel = DaoStringModel(
                        id = 1,
                        language = "da",
                        key = "myKey",
                        value = "hello world"
                    )
                )
                data
                qq
                Log.d("dataaaaaaaa", "${data}")
            } catch (e: IOException) {
                Log.d("MainActivity", "${e}")
            }
        }
    }
    fun getListStrings() {
        viewModelScope.launch {
            try {
                val daoStrings = daoRepository.getAllItems()
                if (daoStrings.hashCode() > 0){
                    api.getListStrings(daoRepository)
                    getAllItems()
                } else {
                    _state.value = emptyList()
                }
            } catch (e: IOException) {
                Log.d("MainActivity", "${e}")
            }
        }
    }

    init {
        getAllItems()
    }

    fun getAllItems() = viewModelScope.launch {
        daoRepository.getAllItems()
            .catch { e->
                Log.d("main", "Exception: ${e.message}")
            }.collect {
                _state.value = it
            }
    }
}
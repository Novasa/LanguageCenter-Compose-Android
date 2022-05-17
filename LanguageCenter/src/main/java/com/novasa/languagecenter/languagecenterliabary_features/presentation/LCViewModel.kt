package com.novasa.languagecenter.languagecenterliabary_features.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.novasa.languagecenter.languagecenterliabary_features.data.repostory.ApiRepository
import com.novasa.languagecenter.languagecenterliabary_features.data.repostory.DaoRepository
import com.novasa.languagecenter.languagecenterliabary_features.data.repostory.hasInternet
import com.novasa.languagecenter.languagecenterliabary_features.domain.api_models.LanguageModel
import com.novasa.languagecenter.languagecenterliabary_features.domain.dao_models.DaoLanguageModel
import com.novasa.languagecenter.languagecenterliabary_features.domain.dao_models.DaoStringModel
import com.novasa.languagecenter.languagecenterliabary_features.provider.ContextProviderImpl

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class LCViewModel @Inject constructor(
    private val daoRepository: DaoRepository,
    private val api: ApiRepository,
): ViewModel() {
    //for dao
    private val _state = MutableStateFlow<List<DaoStringModel>>(emptyList())

    val response: StateFlow<List<DaoStringModel>>
        get() = _state

    fun insert(daoStringModel: DaoStringModel) = viewModelScope.launch {
        daoRepository.insert(daoStringModel)
    }



    fun update(daoStringModel: DaoStringModel) = viewModelScope.launch {
        daoRepository.updateItem(daoStringModel)
    }

    val hasInternet = hasInternet(ContextProviderImpl().context)

    //for apis
    fun getAccountInfo() {
        viewModelScope.launch {
            try {
            } catch (e: IOException) {
                Log.d("MainActivity", "${e}")
            }
        }
    }
    fun getSpecificLanguage() {
        viewModelScope.launch {
            try {
                val data = api.getListLanguages()
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
                val data = api.getListLanguages()
                data
                Log.d("dataaaaaaaa", "${data}")
            } catch (e: IOException) {
                Log.d("MainActivity", "${e}")
            }
        }
    }

    fun getListStrings() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val daoStrings = daoRepository.getAllItems()
                if (daoStrings.isNotEmpty()){
                    if (hasInternet) {
                        api.getListStrings(daoRepository)
                    }
                    _state.value = daoRepository.getAllItems()
                } else {
                    _state.value = emptyList()
                }
            } catch (e: IOException) {
                Log.d("MainActivity", "${e}")
            }
        }
    }
}
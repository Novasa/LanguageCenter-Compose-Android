package com.novasa.languagecenter.languagecenterliabary_features.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.novasa.languagecenter.languagecenterliabary_features.data.repostory.ApiRepository
import com.novasa.languagecenter.languagecenterliabary_features.data.repostory.DaoRepository
import com.novasa.languagecenter.languagecenterliabary_features.domain.dao_models.DaoStringModel
import com.novasa.languagecenter.languagecenterliabary_features.use_cases.hasInternet
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
    private val api: ApiRepository
): ViewModel() {
    enum class Status {
        NOT_INITIALIZED,
        INITIALIZING,
        UPDATING,
        READY,
        FAILED
    }

    var currentStatus = Status.NOT_INITIALIZED

    val _state = MutableStateFlow<List<DaoStringModel>>(emptyList())

    val response: StateFlow<List<DaoStringModel>>
        get() = _state

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

                data

                Log.d("dataaaaaaaa", "${data}")
            } catch (e: IOException) {
                Log.d("MainActivity", "${e}")
            }
        }
    }
    fun getListStrings(): StateFlow<List<DaoStringModel>> {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                var listOfDaoStrings = emptyList<DaoStringModel>()
                val daoStrings = daoRepository.getAllItems()
                if (daoStrings.hashCode() > 0){
                    if (hasInternet()) {
                        currentStatus = Status.UPDATING
                        api.getListStrings(daoRepository)
                    }
                    currentStatus = Status.INITIALIZING
                    _state.value = daoStrings
                } else {
                    _state.value = emptyList()
                }
                currentStatus = Status.READY
            } catch (e: IOException) {
                Log.d("MainActivity", "${e}")
                currentStatus = Status.FAILED
            }
        }
        return response
    }
}
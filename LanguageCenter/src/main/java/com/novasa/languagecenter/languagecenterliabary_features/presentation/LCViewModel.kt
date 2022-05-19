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
import kotlinx.coroutines.delay
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

    private val _state = MutableStateFlow<List<DaoStringModel>>(emptyList())

    val response: StateFlow<List<DaoStringModel>>
        get() = _state

    fun postString (
        platform: String,
        category: String,
        key: String,
        value: String,
        comment: String,
    ) {
        viewModelScope.launch {
            try {
                if (hasInternet()) {
                    api.postString(platform, category, key, value, comment)
                }
            } catch (e: IOException) {
                Log.d("MainActivity", "$e")
            }
        }
    }

    fun deleteDaoTranslations () {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                daoRepository.deleteItem(daoRepository.getAllItems())
                _state.value = daoRepository.getAllItems()
            } catch (e: IOException) {
                Log.d("MainActivity", "$e")
            }
        }
    }

    fun getListStrings(): StateFlow<List<DaoStringModel>> {
        viewModelScope.launch(Dispatchers.IO) {
            currentStatus = Status.INITIALIZING
            try {
                val daoStrings = daoRepository.getAllItems()
                if (daoStrings.hashCode() > 0){
                    if (hasInternet()) {
                        currentStatus = Status.UPDATING
                        api.getListStrings(daoRepository)
                    }
                    _state.value = daoStrings
                } else {
                    _state.value = emptyList()
                }
            } catch (e: IOException) {
                Log.d("MainActivity", "$e")
                currentStatus = Status.FAILED
            }
            if (currentStatus != Status.FAILED){
                currentStatus = Status.READY
            }
        }
        return response
    }
}
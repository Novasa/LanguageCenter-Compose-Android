package com.novasa.languagecenter.languagecenterliabary_features.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.novasa.languagecenter.languagecenterliabary_features.data.repostory.ApiRepository
import com.novasa.languagecenter.languagecenterliabary_features.data.repostory.DaoRepository
import com.novasa.languagecenter.languagecenterliabary_features.domain.dao_models.TranslationEntity
import com.novasa.languagecenter.languagecenterliabary_features.provider.LCProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class LCViewModel @Inject constructor(
    private val daoRepository: DaoRepository,
    private val api: ApiRepository,
    val provider: LCProvider
): ViewModel() {
    enum class Status {
        NOT_INITIALIZED,
        INITIALIZING,
        UPDATING,
        READY,
        FAILED
    }

    private val _currentStatus = MutableStateFlow(Status.NOT_INITIALIZED)

    val currentStatus: StateFlow<Status>
        get() = _currentStatus.asStateFlow()

    private val _state = MutableStateFlow<Flow<List<TranslationEntity>>>(flowOf(emptyList()))

    val response: StateFlow<Flow<List<TranslationEntity>>>
        get() = _state.asStateFlow()

    fun postString (
        platform: String,
        category: String,
        key: String,
        value: String,
        comment: String,
    ) {
        viewModelScope.launch {
            try {
                api.postString(platform, category, key, value, comment)
                getListStrings()
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

    fun getListStrings(): StateFlow<Flow<List<TranslationEntity>>> {
        viewModelScope.launch(Dispatchers.IO) {
            _currentStatus.value = Status.INITIALIZING
            val list = response.value
            try {
                list
                val daoStrings = daoRepository.getAllItems()
                _currentStatus.value = Status.UPDATING
                val data = api.getListStrings()
                for (item in data) {
                    daoRepository.insert(
                        TranslationEntity(
                            key = item.key,
                            value = item.value,
                            language = "da",
                            timestamp = item.timestamp
                        )
                    )
                }
                api.getListStrings()
                _state.value = daoStrings
                _currentStatus.value = Status.READY
            } catch (e: IOException) {
                Log.d("MainActivity", "$e")
                _currentStatus.value = Status.FAILED
            }
        }
        return response
    }
}
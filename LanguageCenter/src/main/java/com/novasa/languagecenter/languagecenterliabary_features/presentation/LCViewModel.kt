package com.novasa.languagecenter.languagecenterliabary_features.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.novasa.languagecenter.languagecenterliabary_features.data.repostory.ApiRepository
import com.novasa.languagecenter.languagecenterliabary_features.data.repostory.DaoRepository
import com.novasa.languagecenter.languagecenterliabary_features.domain.api_models.LanguageModel
import com.novasa.languagecenter.languagecenterliabary_features.domain.api_models.StringModel
import com.novasa.languagecenter.languagecenterliabary_features.domain.dao_models.LanguageEntity
import com.novasa.languagecenter.languagecenterliabary_features.domain.dao_models.TranslationEntity
import com.novasa.languagecenter.languagecenterliabary_features.provider.LCProvider
import com.novasa.languagecenter.languagecenterliabary_features.use_cases.ConfigureLanguage
import com.novasa.languagecenter.languagecenterliabary_features.use_cases.UnixConverter.getDateTime
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.io.IOException
import java.util.Collections.list
import javax.inject.Inject
import javax.inject.Provider

@HiltViewModel
class LCViewModel @Inject constructor(
    private val daoRepository: DaoRepository,
    private val api: ApiRepository,
    val provider: LCProvider,
    val configureLanguage: ConfigureLanguage
): ViewModel() {
    enum class Status {
        NOT_INITIALIZED,
        INITIALIZING,
        UPDATING,
        READY,
        FAILED
    }
    private val daoTranslationsToMap = daoRepository.getTranslations()
        .map { list -> list.associateBy { it.key } }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = emptyMap()
        )

    private val _currentStatus = MutableStateFlow(Status.NOT_INITIALIZED)

    val currentStatus: StateFlow<Status>
        get() = _currentStatus.asStateFlow()

    val translations: StateFlow<Map<String, TranslationEntity>>
        get() = daoTranslationsToMap

    fun ensureTranslationExist(
        category: String,
        key: String,
        value: String,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            _currentStatus.value = Status.UPDATING
            try {
                provider.setCurrentLanguage(
                    configureLanguage.languageConfiguring(
                        languageList = api.getListLanguage(),
                        selectedLanguage = provider.language
                    )
                )
                val mapOfStrings = api.getListStrings(provider.currentLanguage)
                    .associateBy({it.key}, {it})

                if (mapOfStrings[key] == null) {
                    try {
                        api.postString(
                            category = category,
                            key = key,
                            value = value
                        )
                    } catch (e: Exception) {
                        Log.d("MainActivity", "$e")
                    }
                }
            } catch (e: Exception){
                _currentStatus.value = Status.FAILED
            }
            if (_currentStatus.value != Status.FAILED){
                _currentStatus.value = Status.READY
            }
        }
    }

    //funtion til at teste funtionalitet
    fun test () {
        viewModelScope.launch(Dispatchers.IO) {

            Log.d("long", "${daoTranslationsToMap.value}")
            try {
                val remoteLanguageInfo = api.getSpecificLanguage("da")
                daoRepository.deleteItem("da")
                Log.d("long", "${remoteLanguageInfo}")
            } catch (e: Exception) {

            }
            Log.d("long", "${daoTranslationsToMap.value}")
        }
    }

    fun getListStrings() {
        viewModelScope.launch(Dispatchers.IO) {

            _currentStatus.value = Status.INITIALIZING
            try {
                provider.setCurrentLanguage(
                    configureLanguage.languageConfiguring(
                        languageList = api.getListLanguage(),
                        selectedLanguage = provider.language
                    )
                )
                val LanguageInfoNullVal: Long = 0
                val localLanguageInfo = daoRepository.getLanguageInfo()
                val remoteLanguageInfo = api.getSpecificLanguage(provider.currentLanguage)
                val remoteTranslations = api.getListStrings(provider.currentLanguage)

                if (remoteLanguageInfo.timestamp >= localLanguageInfo
                    && remoteLanguageInfo.timestamp != localLanguageInfo
                ) {
                    _currentStatus.value = Status.UPDATING
                    if (localLanguageInfo != LanguageInfoNullVal) {
                        daoRepository.deleteItem(remoteLanguageInfo.codename)
                    }

                    daoRepository.insertLanguage(
                        languageEntity = LanguageEntity(
                            name = remoteLanguageInfo.name,
                            codename =  remoteLanguageInfo.codename,
                            is_fallback =  remoteLanguageInfo.is_fallback,
                            timestamp = remoteLanguageInfo.timestamp
                        )
                    )
                    daoRepository.insertTranslations(
                        remoteTranslations.map {
                            TranslationEntity(
                                key = it.key,
                                value = it.value,
                                language = it.language,
                            )
                        }
                    )
                }
            } catch (e: Exception) {
                _currentStatus.value = Status.FAILED
            }
            if (_currentStatus.value != Status.FAILED){
                _currentStatus.value = Status.READY
            }
        }
    }
}
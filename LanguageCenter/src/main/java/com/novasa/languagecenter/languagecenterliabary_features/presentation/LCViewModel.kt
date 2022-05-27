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
import com.novasa.languagecenter.languagecenterliabary_features.use_cases.HasInternet
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
    private val hasInternet: HasInternet,
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

    val response: StateFlow<Map<String, TranslationEntity>>
        get() = daoTranslationsToMap

    fun getReponse(
        category: String,
        key: String,
        value: String,
    ): Flow<TranslationEntity?> {
        val stateFlowOfTranslationEntity = flowOf(daoTranslationsToMap.value[key]).stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = TranslationEntity("","","")
        )

        viewModelScope.launch(Dispatchers.IO) {
            provider.setCurrentLanguage(
                configureLanguage.languageConfiguring(
                    languageList = api.getListLanguage(),
                    selectedLanguage = provider.language
                )
            )

            val mapOfStrings = api.getListStrings(provider.currentLanguage)
                .associateBy({it.key}, {it})

            if (mapOfStrings[key] == null) {
                if (hasInternet.hasInternet()) {
                    postString(
                        category = category,
                        key = key,
                        value = value
                    )
                }
            }
            if (daoTranslationsToMap.value[key] == null) {
                Log.d("best flow", "hello")
                daoRepository.insertTranslation(
                    TranslationEntity(
                        key = key,
                        value = value,
                        language = category
                    )
                )
            }
        }

        return responeInstance(stateFlowOfTranslationEntity).response
    }

    fun morphtest() {
        Log.d("best flow", daoTranslationsToMap.value.toString())
    }


    fun postString (
        category: String,
        key: String,
        value: String,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                api.postString(category, key, value)
            } catch (e: IOException) {
                Log.d("MainActivity", "$e")
            }
        }
    }

    fun getListStrings() {
        viewModelScope.launch(Dispatchers.IO) {
            _currentStatus.value = Status.INITIALIZING
            provider.setCurrentLanguage(
                configureLanguage.languageConfiguring(
                    languageList = api.getListLanguage(),
                    selectedLanguage = provider.language
                )
            )
            try {
                val localLanguageInfo = if (daoRepository.getLanguageInfo() == null) {
                    daoRepository.getLanguageInfo()
                } else {
                    LanguageEntity(
                        "",
                        "",
                        false,
                        0
                    )
                }
                val remoteLanguageInfo = if (hasInternet.hasInternet()) {
                    api.getSpecificLanguage(provider.currentLanguage)
                } else {
                    LanguageModel(
                        name = "danish",
                        codename = "da",
                        is_fallback = false,
                        timestamp = 0,
                    )
                }

                val remoteTranslations = if (hasInternet.hasInternet()) {
                    api.getListStrings(provider.currentLanguage)
                } else {
                    listOf<StringModel>(
                        StringModel(
                        key = "",
                        value = "",
                        language = "",
                        htmlTags = arrayOf("",""),
                    ))
                }
                if (localLanguageInfo == null || getDateTime(remoteLanguageInfo.timestamp) >= localLanguageInfo.timestamp
                    && getDateTime(remoteLanguageInfo.timestamp) != localLanguageInfo.timestamp
                ) {
                    _currentStatus.value = Status.UPDATING

                    if(hasInternet.hasInternet()){
                        if (localLanguageInfo != null) {
                            daoRepository.deleteItem(daoRepository.getLanguageInfo())
                        }
                    }
                    daoRepository.insertLanguage(
                        languageEntity = LanguageEntity(
                            name = remoteLanguageInfo.name,
                            codename =  remoteLanguageInfo.codename,
                            is_fallback =  remoteLanguageInfo.is_fallback,
                            timestamp = getDateTime(remoteLanguageInfo.timestamp)
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
            } catch (e: IOException) {
                Log.d("MainActivity", "$e")
                _currentStatus.value = Status.FAILED
            }
            if (_currentStatus.value != Status.FAILED){
                _currentStatus.value = Status.READY
            }
        }
        Log.d("hello", daoTranslationsToMap.value.toString())
    }
}

class responeInstance(
    private val stateFlowOfTranslationEntity: StateFlow<TranslationEntity?>
) {
    val response: StateFlow<TranslationEntity?>
        get() = stateFlowOfTranslationEntity
}

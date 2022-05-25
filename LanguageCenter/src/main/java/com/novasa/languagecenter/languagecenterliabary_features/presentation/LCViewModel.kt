package com.novasa.languagecenter.languagecenterliabary_features.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.novasa.languagecenter.languagecenterliabary_features.data.repostory.ApiRepository
import com.novasa.languagecenter.languagecenterliabary_features.data.repostory.DaoRepository
import com.novasa.languagecenter.languagecenterliabary_features.domain.api_models.AccountInfoModel
import com.novasa.languagecenter.languagecenterliabary_features.domain.api_models.StringModel
import com.novasa.languagecenter.languagecenterliabary_features.domain.dao_models.LanguageEntity
import com.novasa.languagecenter.languagecenterliabary_features.domain.dao_models.TranslationEntity
import com.novasa.languagecenter.languagecenterliabary_features.provider.LCProvider
import com.novasa.languagecenter.languagecenterliabary_features.use_cases.ConfigureLanguage
import com.novasa.languagecenter.languagecenterliabary_features.use_cases.UnixConverter.getDateTime
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.io.IOException
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

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

    private val _currentStatus = MutableStateFlow(Status.NOT_INITIALIZED)

    val currentStatus: StateFlow<Status>
        get() = _currentStatus.asStateFlow()

    val response: StateFlow<Map<String?, TranslationEntity>>
        get() = daoRepository.getTranslations()
            .map { list -> list.associateBy { it.key } }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.Eagerly,
                initialValue = emptyMap()
            )

    fun postString (
        platform: String,
        category: String,
        key: String,
        value: String,
        html: String,
    ) {
        viewModelScope.launch {
            try {
                api.postString(platform, category, key, value, html)
                getListStrings()
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
                val localLanguageInfo = daoRepository.getLanguageInfo()
                val remoteLanguageInfo = api.getSpecificLanguage(provider.currentLanguage)
                val remoteTranslations = api.getListStrings(provider.currentLanguage)

                Log.d("html", "${remoteTranslations}")

                if (true){
                    _currentStatus.value = Status.UPDATING
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

                } else if (
                    getDateTime(remoteLanguageInfo.timestamp) >= localLanguageInfo.timestamp
                    && getDateTime(remoteLanguageInfo.timestamp) != localLanguageInfo.timestamp
                ) {
                    _currentStatus.value = Status.UPDATING
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
    }
}
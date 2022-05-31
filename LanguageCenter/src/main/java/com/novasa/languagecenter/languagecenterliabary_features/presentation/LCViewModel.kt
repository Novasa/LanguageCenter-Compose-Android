package com.novasa.languagecenter.languagecenterliabary_features.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.novasa.languagecenter.languagecenterliabary_features.data.repostory.ApiRepository
import com.novasa.languagecenter.languagecenterliabary_features.data.repostory.DaoRepository
import com.novasa.languagecenter.languagecenterliabary_features.domain.api_models.LanguageModel
import com.novasa.languagecenter.languagecenterliabary_features.domain.dao_models.LanguageEntity
import com.novasa.languagecenter.languagecenterliabary_features.domain.dao_models.TranslationEntity
import com.novasa.languagecenter.languagecenterliabary_features.provider.LCProvider
import com.novasa.languagecenter.languagecenterliabary_features.use_cases.ConfigureLanguage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

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
                        languageList = api.getListLanguage().associateBy({it.codename}, {it}),
                        selectedLanguage = provider.language
                    )
                )
                val mapOfStrings = daoTranslationsToMap.value

                if (mapOfStrings[key] == null) {
                    api.postString(
                        category = category,
                        key = key,
                        value = value
                    )
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
                val remoteTranslations = api.getListStrings("off", "da")
                Log.d("data", "${remoteTranslations}")
            } catch (e: Exception) {
                Log.e("long", "", e)
            }
            Log.d("long", "${daoTranslationsToMap.value}")
        }
    }

    fun getListStrings() {
        viewModelScope.launch(Dispatchers.IO) {

            _currentStatus.value = Status.INITIALIZING
            try {
                val remoteLanguages = api.getListLanguage()
                    .associateBy({it.codename}, {it})

                provider.setCurrentLanguage(
                    configureLanguage.languageConfiguring(
                        languageList = remoteLanguages,
                        selectedLanguage = provider.language
                    )
                )
                val localLanguageInfo = daoRepository.getLanguageInfo(provider.currentLanguage)
                val remoteLanguageInfo = remoteLanguages[provider.currentLanguage]

                if (remoteLanguageInfo?.timestamp ?: 0 >= localLanguageInfo
                    && remoteLanguageInfo?.timestamp ?: 0 != localLanguageInfo
                ) {
                    _currentStatus.value = Status.UPDATING
                    if (localLanguageInfo != 0L) {
                        daoRepository.deleteItem(remoteLanguageInfo!!.codename)
                    }

                    val remoteTranslations = api.getListStrings(provider.currentLanguage, "da")

                    daoRepository.insertLanguage(
                        languageEntity = LanguageEntity(
                            name = remoteLanguageInfo!!.name,
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
                    Log.d("data", remoteTranslations.toString())
                }
            } catch (e: Exception) {
                Log.e("LC", "Update failed", e)
                _currentStatus.value = Status.FAILED
            }
            if (_currentStatus.value != Status.FAILED){
                _currentStatus.value = Status.READY
            }
        }
    }
}
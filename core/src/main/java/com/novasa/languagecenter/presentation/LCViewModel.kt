package com.novasa.languagecenter.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.novasa.languagecenter.data.repostory.ApiRepository
import com.novasa.languagecenter.data.repostory.DaoRepository
import com.novasa.languagecenter.domain.api_models.LanguageModel
import com.novasa.languagecenter.domain.api_models.StringModel
import com.novasa.languagecenter.domain.dao_models.LanguageEntity
import com.novasa.languagecenter.domain.dao_models.TranslationEntity
import com.novasa.languagecenter.provider.LCProvider
import com.novasa.languagecenter.use_cases.ConfigureLanguage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LCViewModel @Inject constructor(
    val provider: LCProvider,
    private val daoRepository: DaoRepository,
    private val api: ApiRepository,
    private val configureLanguage: ConfigureLanguage
) : ViewModel() {

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

    private var remoteTranslations = listOf<StringModel>()

    val currentStatus: StateFlow<Status>
        get() = _currentStatus.asStateFlow()

    val translations: StateFlow<Map<String, TranslationEntity>>
        get() = daoTranslationsToMap

    fun ensureTranslationExist(
        key: String,
        value: String,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                Log.d("Funtion_exucuted", "ensureTranslationExist")
                val mapOfStrings = daoTranslationsToMap.value

                if (mapOfStrings[key] == null) {
                    api.postString(key = key, value = value, category = provider.language)
                }

            } catch (e: Exception) {
                Log.e("Funtion_exucuted", "ensureTranslationExist_failed", e)
            }
        }
    }

    //funtion til at teste funtionalitet
    fun test() {
        viewModelScope.launch(Dispatchers.IO) {

            Log.d("long", "$remoteTranslations")
            try {
                val remoteTranslations = TranslationEntity(
                    key = daoTranslationsToMap.value["Begravelse eller bisættelse"]?.key ?: "virker ik",
                    value = daoTranslationsToMap.value["Begravelse eller bisættelse"]?.value ?: "virker ik",
                    language = daoTranslationsToMap.value["Begravelse eller bisættelse"]?.language ?: "na",
                )
                val yesss = api.postString(
                    category = remoteTranslations.language,
                    key = remoteTranslations.key,
                    value = remoteTranslations.value
                )
                Log.d("data bata", "${yesss}")
            } catch (e: Exception) {
                Log.e("long", "", e)
            }
            Log.d("long", "$remoteTranslations")
        }
    }

    fun initialize() {
        viewModelScope.launch(Dispatchers.IO) {

            _currentStatus.value = Status.INITIALIZING
            try {
                val remoteLanguages = api.getListLanguage()

                val remoteLanguageInfo: LanguageModel = configureLanguage.languageConfiguring(
                    languageList = remoteLanguages,
                    selectedLanguage = provider.language
                )

                val localLanguageInfo: LanguageEntity? = daoRepository.getLanguageInfo(remoteLanguageInfo.codename)

                if (localLanguageInfo == null || localLanguageInfo.timestamp < remoteLanguageInfo.timestamp) {

                    Log.d("Funtion_exucuted", "Timestamp check $remoteLanguageInfo $localLanguageInfo")

                    _currentStatus.value = Status.UPDATING

                    remoteTranslations = api.getListStrings(language = remoteLanguageInfo.codename)

                    daoRepository.updateDaoDb(
                        translationEntity = remoteTranslations.map {
                            TranslationEntity(
                                key = it.key,
                                value = it.value,
                                language = it.language,
                            )
                        },
                        languageEntity = with(remoteLanguageInfo) {
                            LanguageEntity(
                                name = name,
                                codename = codename,
                                is_fallback = is_fallback,
                                timestamp = timestamp
                            )
                        }
                    )
                }
            } catch (e: Exception) {
                Log.e("LC", "Update failed", e)
                _currentStatus.value = Status.FAILED
            }
            if (_currentStatus.value != Status.FAILED) {
                _currentStatus.value = Status.READY
            }
        }
    }
}

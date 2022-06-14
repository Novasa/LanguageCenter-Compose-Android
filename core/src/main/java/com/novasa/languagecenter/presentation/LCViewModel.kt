package com.novasa.languagecenter.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.novasa.languagecenter.data.repostory.ApiRepository
import com.novasa.languagecenter.data.repostory.DaoRepository
import com.novasa.languagecenter.domain.api_models.LanguageModel
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

    private val _status = MutableStateFlow(Status.NOT_INITIALIZED)
    val status: StateFlow<Status>
        get() = _status.asStateFlow()

    val translations = daoRepository.getTranslations()
        .map { list -> list.associateBy { it.key } }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = emptyMap()
        )

    fun ensureTranslationExist(
        category: String,
        key: String,
        value: String,
        html: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            if (!daoRepository.doesTranslationExist(category = category, key = key)) {
                try {
                    api.postString(
                        category = category,
                        key = key,
                        value = value,
                        html = html
                    )

                } catch (e: Exception) {
                    Log.e("LanguageCenter", "ensureTranslationExist failed", e)
                }
            }
        }
    }


    fun initialize() {
        viewModelScope.launch(Dispatchers.IO) {

            _status.value = Status.INITIALIZING
            try {
                val remoteLanguages = api.getListLanguage()

                val remoteLanguageInfo: LanguageModel = configureLanguage.languageConfiguring(
                    languageList = remoteLanguages,
                    selectedLanguage = provider.language
                )

                val localLanguageInfo: LanguageEntity? = daoRepository.getLanguageInfo(remoteLanguageInfo.codename)

                if (localLanguageInfo == null || localLanguageInfo.timestamp < remoteLanguageInfo.timestamp) {

                    Log.d("LanguageCenter", "Timestamp check $remoteLanguageInfo $localLanguageInfo")

                    _status.value = Status.UPDATING

                    val remoteTranslations = api.getListStrings(language = remoteLanguageInfo.codename)

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
                Log.e("LanguageCenter", "Update failed", e)
                _status.value = Status.FAILED
            }
            if (_status.value != Status.FAILED) {
                _status.value = Status.READY
            }
        }
    }
}

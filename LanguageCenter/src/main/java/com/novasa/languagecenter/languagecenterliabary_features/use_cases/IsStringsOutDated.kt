package com.novasa.languagecenter.languagecenterliabary_features.use_cases

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.novasa.languagecenter.languagecenterliabary_features.data.repostory.ApiRepository
import com.novasa.languagecenter.languagecenterliabary_features.domain.api_models.LanguageModel
import com.novasa.languagecenter.languagecenterliabary_features.domain.dao_models.DaoLanguageModel
import org.intellij.lang.annotations.Language
import java.util.*

fun IsStringsOutDated(
    languageFromApi: List<DaoLanguageModel>,
    FromApi: List<LanguageModel>,
): Boolean {

    return true
}
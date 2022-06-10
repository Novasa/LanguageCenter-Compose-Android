package com.novasa.languagecenter.domain.api_models

import kotlinx.serialization.Serializable

@Serializable
data class AccountInfoModel(
    val account_name: String,
)

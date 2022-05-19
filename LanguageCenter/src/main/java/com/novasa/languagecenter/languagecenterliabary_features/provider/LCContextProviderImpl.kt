package com.novasa.languagecenter.languagecenterliabary_features.provider

import android.content.Context
import javax.inject.Inject

class LCContextProviderImpl @Inject constructor(

): LCContextProvider {

    override val context: Context
        get() = _context

    override fun setContext(context: Context) {
        _context = context
    }
    //fordi jeg ik ved hvordan jeg får adgang til bind
    companion object {
        lateinit var _context: Context
    }
}
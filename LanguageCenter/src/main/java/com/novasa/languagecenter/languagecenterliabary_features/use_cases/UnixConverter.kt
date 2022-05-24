package com.novasa.languagecenter.languagecenterliabary_features.use_cases

import java.sql.Date
import java.text.SimpleDateFormat

object UnixConverter {
    fun getDateTime(s: Int): Int {
        try {
            val sdf = SimpleDateFormat("ddMMyyyy")
            val netDate = Date(s.toLong() * 1000)
            return sdf.format(netDate).toInt()
        } catch (e: Exception) {
            return 0
        }
    }
}
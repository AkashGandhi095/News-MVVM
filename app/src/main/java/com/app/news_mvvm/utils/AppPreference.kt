package com.app.news_mvvm.utils

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import androidx.core.content.edit

class AppPreference(context: Context) {
    private val pref :SharedPreferences =
        context.getSharedPreferences(AppConstants.PREF_FILE_NAME , MODE_PRIVATE)

    fun editActivityStatus(boolean: Boolean) {
        pref.edit {
            putBoolean(AppConstants.PREF_ACTIVITY_STATUS , boolean)
        }
    }

    val isSelectionDone :Boolean
        get() = pref.getBoolean(AppConstants.PREF_ACTIVITY_STATUS , false)

    fun editCountryCode(countryCode :String) {
        pref.edit {
            putString(AppConstants.PREF_COUNTRY_CODE , countryCode)
        }
    }

    val prefCountryCode :String
        get() = pref.getString(AppConstants.PREF_COUNTRY_CODE , "")?:""
}
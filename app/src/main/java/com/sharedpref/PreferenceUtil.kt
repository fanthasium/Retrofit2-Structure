package com.sharedpref

import android.content.Context
import android.content.SharedPreferences

class PreferenceUtil(context: Context) {

    companion object {
        const val ACCESS_TOKEN = "ACCESS_TOKEN"
        const val REFRESH_TOKEN = "REFRESH_TOKEN"
        const val CHECK_BOX = "CHECK_BOX"
    }

    private val prefs: SharedPreferences =
        context.getSharedPreferences("prefs_name", Context.MODE_PRIVATE)


    fun getString(key: String, defValue: String?): String {
        return prefs.getString(key, defValue).toString()
    }

    fun setString(key: String, str: String) {
        prefs.edit().putString(key, str).apply()
    }

    fun remove(key: String) {
        prefs.edit().remove(key).apply()
    }


}

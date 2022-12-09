package com

import android.app.Application
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class App: Application() {

    companion object{
        lateinit var prefs: PreferenceUtil
    }
    override fun onCreate() {

        prefs = PreferenceUtil(applicationContext)
        super.onCreate()
    }
}
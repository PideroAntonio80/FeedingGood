package com.diusframi.feedinggood

import android.app.Application
import com.diusframi.feedinggood.data.localdb.database.FeedingGoodDatabase
import com.diusframi.feedinggood.data.preferences.PreferencesController

class FeedingGoodApplication : Application() {

    companion object {
        lateinit var preferences: PreferencesController
    }

    override fun onCreate() {
        super.onCreate()

        FeedingGoodDatabase.createDb(this)

        preferences = PreferencesController(applicationContext)
    }
}
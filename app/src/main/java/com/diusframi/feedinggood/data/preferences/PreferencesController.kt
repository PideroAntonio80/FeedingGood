package com.diusframi.feedinggood.data.preferences

import android.content.Context

class PreferencesController(val context: Context) {

    val SHARED_PREFERENCES_NAME = "mySharedPreferencesName"

    val MY_USER_NAME = "myUserName"
    val MY_USER_PASS = "myUserPass"

    val storage = context.getSharedPreferences(SHARED_PREFERENCES_NAME, 0)

    fun saveUserName(name: String) {
        storage.edit().putString(MY_USER_NAME, name).apply()
    }

    fun saveUserPass(pass: String) {
        storage.edit().putString(MY_USER_PASS, pass).apply()
    }

    fun getUserName(): String? {
        return storage.getString(MY_USER_NAME, "")
    }

    fun getUserPass(): String? {
        return storage.getString(MY_USER_PASS, "")
    }
}
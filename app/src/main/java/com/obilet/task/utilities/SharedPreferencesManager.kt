package com.obilet.task.utilities

import android.content.Context
import android.content.SharedPreferences


import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton
/**
 * The SharedPreferences local-database for this app
 */
@Singleton
class SharedPreferencesManager @Inject constructor(@ApplicationContext private val context: Context) {

    private val sharedPreferences: SharedPreferences by lazy {
        context.getSharedPreferences("my_app_preferences", Context.MODE_PRIVATE)
    }

    fun getString(key: String, defaultValue: String): String? {
        return sharedPreferences.getString(key, defaultValue)
    }

    fun putString(key: String, value: String) {
        sharedPreferences.edit().putString(key, value).apply()
    }


}
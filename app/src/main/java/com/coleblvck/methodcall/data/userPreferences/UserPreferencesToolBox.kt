package com.coleblvck.methodcall.data.userPreferences

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch


const val USER_PREFERENCES_KEY = "userPreferences"
val Context.methodCallDataStore by preferencesDataStore(name = USER_PREFERENCES_KEY)

class UserPreferencesToolBox(
    private val store: DataStore<Preferences>
) {
    val userPreferences = UserPreferences(
        superuserEnabled = mutableStateOf(
            false
        )
    )

    fun updateSuperuserEnabledStatus(value: Boolean) {
        userPreferences.superuserEnabled.value = value
        saveBoolean(PreferenceKeys.SUPERUSER_ENABLED, value)
    }

    private fun saveBoolean(key: Preferences.Key<Boolean>, value: Boolean) {
        CoroutineScope(Dispatchers.Default).launch {
            store.edit { preferences -> preferences[key] = value }
        }
    }


    init {
        CoroutineScope(Dispatchers.IO).launch {
            val preferences = store.data.first()
            userPreferences.superuserEnabled.value = preferences[PreferenceKeys.SUPERUSER_ENABLED]
                ?: userPreferences.superuserEnabled.value
        }
    }

    private object PreferenceKeys {
        val SUPERUSER_ENABLED = booleanPreferencesKey("superuserEnabled")
    }
}
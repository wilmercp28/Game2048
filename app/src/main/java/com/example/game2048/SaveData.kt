package com.example.game2048

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
suspend fun saveData(score: Int, context: Context, dataStore: DataStore<Preferences>) {
    val scoreKey = intPreferencesKey("Score")
    dataStore.edit { preferences ->
        preferences[scoreKey] = score
    }
}

suspend fun loadData(context: Context, dataStore: DataStore<Preferences>): Int {
    val scoreKey = intPreferencesKey("Score")
    val preferences = dataStore.data.first()
    return preferences[scoreKey] ?: 0
}


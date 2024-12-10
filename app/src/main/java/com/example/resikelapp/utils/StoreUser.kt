package com.example.resikelapp.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class StoreUser(private val context: Context) {

    // to make sure there's only one instance
    companion object {
        private val Context.dataStoree: DataStore<Preferences> by preferencesDataStore("user")
        val USER_NAME_KEY = stringPreferencesKey("user_name")
        val USER_PHOTO_KEY = stringPreferencesKey("user_photo")
    }

    val getName: Flow<String?> = context.dataStoree.data
        .map { preferences ->
            preferences[USER_NAME_KEY]
        }

    val getEmail: Flow<String?> = context.dataStoree.data
        .map { preferences ->
            preferences[USER_PHOTO_KEY]
        }

    suspend fun saveName(name: String) {
        context.dataStoree.edit { preferences ->
            preferences[USER_NAME_KEY] = name
        }
    }

    suspend fun savePhoto(name: String) {
        context.dataStoree.edit { preferences ->
            preferences[USER_PHOTO_KEY] = name
        }
    }


}
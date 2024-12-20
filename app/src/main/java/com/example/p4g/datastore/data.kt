package com.example.p4g.datastore

// In a Kotlin file, e.g., DataStoreModule.kt
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.p4g.model.Persona
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "favorites")
val FAVORITES_KEY = stringPreferencesKey("favorite_personas")

// Save favorites
suspend fun saveFavorites(context: Context, favorites: List<Persona>) {
    val jsonString = Json.encodeToString(favorites)
    context.dataStore.edit { preferences ->
        preferences[FAVORITES_KEY] = jsonString
        println("Saved Favorites: $jsonString") // Debug log
    }
}

fun getFavorites(context: Context): Flow<List<Persona>> {
    return context.dataStore.data
        .map { preferences ->
            val jsonString = preferences[FAVORITES_KEY] ?: ""
            println("Retrieved Favorites: $jsonString") // Debug log
            if (jsonString.isNotEmpty()) Json.decodeFromString(jsonString) else emptyList()
        }
}
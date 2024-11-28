package com.example.p4g.HTTP

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.p4g.model.Entity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PersonaViewModel : ViewModel() {
    private val _personas = MutableStateFlow<Map<String, Entity>?>(null)
    val personas: StateFlow<Map<String, Entity>?> = _personas

    init {
        fetchPersonas()
    }

    private fun fetchPersonas() {
        // Launch the network call in an IO thread for efficiency
        viewModelScope.launch {
            try {
                // Fetch the personas asynchronously
                val fetchedPersonas = RetrofitInstance.api.getPersonas()

                // Log the fetched data to verify the response
                Log.d("PersonaViewModel", "Fetched Personas: $fetchedPersonas")

                // Update the StateFlow with the fetched data
                _personas.value = fetchedPersonas
            } catch (f: Exception) {
                // Handle error appropriately (e.g., logging or user notification)
                f.printStackTrace()
            }
        }
    }

}


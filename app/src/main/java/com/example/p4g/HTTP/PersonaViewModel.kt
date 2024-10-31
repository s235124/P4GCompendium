package com.example.p4g.HTTP

import androidx.lifecycle.ViewModel
import com.example.p4g.Entity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
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
        CoroutineScope(Dispatchers.IO).launch {
            try {
                _personas.value = RetrofitInstance.api.getPersonas()
            }
            catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
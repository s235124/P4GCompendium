package com.example.p4g.HTTP

import androidx.lifecycle.ViewModel
import com.example.p4g.Entity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
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
        println("Fetching")
        CoroutineScope(Dispatchers.Main).launch {
            try {
                println("Trying")
                _personas.value = RetrofitInstance.api.getPersonas()
                delay(1000)
            }
            catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
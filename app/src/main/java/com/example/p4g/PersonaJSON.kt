package com.example.p4g

import com.example.p4g.HTTP.PersonaViewModel
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.Serializable

@Serializable
data class Entity(
    val inherits: String,
    val lvl: Int,
    val race: String,
    val resists: String,
    val skills: Map<String, Double>,
    val stats: List<Int>
)

fun main() : Unit = runBlocking {

    val personaViewModel = PersonaViewModel()

    // Deserialize the JSON into a map
    val entityMap = personaViewModel.personas.value

    // Iterate through the map to get the names of each persona
    personaViewModel.personas.collect { entityMap ->
        if (entityMap != null) {
            for (name in entityMap.values) {
                println(name) // Prints the name of each persona
            }
        }
    }
}
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

class PersonaJSON {
    public fun makeList() : ArrayList<Map.Entry<String, Entity>> = runBlocking {

        val personaViewModel = PersonaViewModel()

        var personaList = arrayListOf<Map.Entry<String, Entity>>()

        val entityMap = personaViewModel.personas.value

        // Iterate through the map to get the names of each persona
        personaViewModel.personas.collect { entityMap ->
            if (entityMap != null) {
                for (persona in entityMap) {
                    personaList.add(persona)
                }
            }
        }

        return@runBlocking personaList
    }
}
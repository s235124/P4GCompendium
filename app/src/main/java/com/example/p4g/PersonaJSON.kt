package com.example.p4g

import android.util.Log
import com.example.p4g.HTTP.PersonaViewModel
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
    companion object {
        fun makeList(personaViewModel: PersonaViewModel): ArrayList<Persona> {
            val personaList = arrayListOf<Persona>()

            // Ensure personas are loaded
            personaViewModel.personas.value?.let { personas ->
                // Iterate through the map
                personas.forEach { persona ->
                    val p = Persona(
                        persona.key,
                        persona.value.inherits,
                        persona.value.lvl,
                        persona.value.race,
                        persona.value.resists
                    )

                    for (skill in persona.value.skills) {
                        p.skills.add(Skills(skill.key, skill.value))
                    }

                    for (stat in persona.value.stats) {
                        p.stats.add(stat)
                    }

                    personaList.add(p)
                }
            } ?: run {
                Log.e("PersonaJSON", "No personas available.")
            }

            return personaList
        }
    }
}

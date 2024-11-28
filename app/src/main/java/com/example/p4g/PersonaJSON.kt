package com.example.p4g

import android.util.Log
import com.example.p4g.model.Entity
import com.example.p4g.model.Persona
import com.example.p4g.model.Skills

class PersonaJSON {
    companion object {
        fun makeList(personas: Map<String, Entity>?): ArrayList<Persona> {
            val personaList = arrayListOf<Persona>()

            if (personas != null && personas.isNotEmpty()) {
                // Iterate through the map
                personas.forEach { persona ->
                    val p = Persona(
                        name = persona.key,
                        inherits = persona.value.inherits.replaceFirstChar { char -> char.titlecaseChar() },
                        level = persona.value.lvl,
                        race = persona.value.race,
                        resists = persona.value.resists
                    )

                    // Add skills
                    persona.value.skills.forEach { skill ->
                        p.skills.add(Skills(skill.key, skill.value))
                    }

                    // Add stats
                    persona.value.stats.forEach { stat ->
                        p.stats.add(stat)
                    }

                    personaList.add(p)
                }
            } else {
                Log.e("PersonaJSON", "No personas available.")
            }

            return personaList
        }
    }
}


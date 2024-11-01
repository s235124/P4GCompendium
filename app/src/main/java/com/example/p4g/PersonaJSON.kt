package com.example.p4g

import com.example.p4g.HTTP.PersonaViewModel
import kotlinx.serialization.Serializable

@Serializable
data class Entity(
    val inherits: String,
    val lvl: Int,
    val race: String,
    val resists: String,
    val skills: ArrayList<Skills>,
    val stats: List<Int>
)

//fun main () {
//    println("Making list")
//    var i = 0
//    val p = PersonaJSON.makeList()
//    for (persona in p) {
//        i++
//        println("Persona number $i\n")
//        println(persona.name)
//    }
//    println("list is done")
//}

class PersonaJSON {
    companion object {
        fun makeList(): ArrayList<Persona> {

            val personaViewModel = PersonaViewModel()

            val personaList = arrayListOf<Persona>()


            // Iterate through the map
            personaViewModel.personas.value?.forEach { persona ->
                val p= Persona(
                    persona.key,
                    persona.value.inherits,
                    persona.value.lvl,
                    persona.value.race,
                    persona.value.resists
                )

                for (skill in persona.value.skills) {
                    p.skills.add(Skills(skill.skillName, skill.gainedAtLevel))
                }

                for (stat in persona.value.stats) {
                    p.stats.add(stat)
                }

                personaList.add(p)
            }

            return personaList
        }
    }
}
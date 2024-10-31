package com.example.p4g

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json


@Serializable
data class Entity(
    val inherits: String,
    val lvl: Int,
    val race: String,
    val resists: String,
    val skills: Map<String, Double>,
    val stats: List<Int>
)

fun main() {
    val jsonString = """
    {
        "Abaddon": {
            "inherits": "ailment",
            "lvl": 55,
            "race": "Tower",
            "resists": "-ns--wr-",
            "skills": {
                "Old One": 0.1,
                "Mudoon": 0.2,
                "Arrow Rain": 0.3,
                "Agidyne": 56,
                "Endure Light": 57,
                "Tetra Break": 60,
                "Null Physical": 62
            },
            "stats": [43, 27, 50, 23, 29]
        },
        "Alice": {
            "inherits": "dark",
            "lvl": 72,
            "race": "Death",
            "resists": "-----wr-",
            "skills": {
                "Mamudoon": 0.1,
                "Mudo Boost": 0.2,
                "Dekunda": 0.3,
                "Endure Light": 75,
                "Megidola": 76,
                "Mind Charge": 77,
                "Die for Me!": 79
            },
            "stats": [39, 56, 33, 45, 44]
        }
    }
    """

    // Deserialize the JSON into a map
    val entityMap = Json.decodeFromString<Map<String, Entity>>(jsonString)

    // Iterate through the map to get the names of each persona
    for (name in entityMap.keys) {
        println(name) // Prints the name of each persona
    }
}

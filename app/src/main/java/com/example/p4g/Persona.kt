package com.example.p4g

data class Persona (
    val name: String,
    val inherits: String,
    val level: Int,
    val race: String,
    val resists: String,
    val skills: ArrayList<Skills>,
    val stats: ArrayList<Int>
) {
    // Constructor without skills and stats
    constructor(name: String,
                inherits: String,
                level: Int,
                race: String,
                resists: String) : this(name, inherits, level, race, resists, arrayListOf(), arrayListOf())

    // Constructor which transforms skills and stats
    constructor(name: String,
        inherits: String,
        level: Int,
        race: String,
        resists: String,
        skills: Map<String, Double>,
        stats: List<Int>) : this(name, inherits, level, race, resists) {
        for (skill in skills) {
            this.skills.add(Skills(skill.key, skill.value))
        }

        for (stat in stats) {
            this.stats.add(stat)
        }
    }
}
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
}
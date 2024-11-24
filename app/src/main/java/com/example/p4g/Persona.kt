package com.example.p4g

import androidx.annotation.DrawableRes

data class Persona (
    @DrawableRes val img: Int,
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
                resists: String) : this(R.drawable.ic_launcher_background, name, inherits, level, race, resists, arrayListOf(), arrayListOf())

}

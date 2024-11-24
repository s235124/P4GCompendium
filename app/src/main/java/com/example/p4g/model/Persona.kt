package com.example.p4g.model

import androidx.annotation.DrawableRes
import com.example.p4g.R
import kotlinx.serialization.Serializable

@Serializable
data class Persona (
    @DrawableRes var img: Int,
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
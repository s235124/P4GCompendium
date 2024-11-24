
package com.example.p4g.model

import androidx.annotation.DrawableRes

data class ListItem(
    val name: String,
    @DrawableRes val img: Int,
    val level : Int,
    val race : String
)
{
    constructor(persona: Persona) : this(name = persona.name, img = persona.img, level = persona.level, race = persona.race)
}

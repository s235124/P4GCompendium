
package com.example.p4g.model

import androidx.annotation.DrawableRes
import com.example.p4g.R

data class ListItem(
    val name: String,
    @DrawableRes val img: Int,
    val level : Int,
    val race : String
)
{
    constructor(persona: Persona) : this(name = persona.name, img = R.drawable.sandman, level = persona.level, race = persona.race)
}


package com.example.p4g.listItems

import androidx.annotation.DrawableRes
import com.example.p4g.Entity

data class ListItem(
    val name: String,
    @DrawableRes val img: Int,
    val info: Entity
)

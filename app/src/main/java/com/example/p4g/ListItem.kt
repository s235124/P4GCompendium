
package com.example.p4g.listItems
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Listitem(
    @StringRes val name: Int,
    @DrawableRes val img: Int
)

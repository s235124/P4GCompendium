package com.example.p4g.navigation

sealed class Route(val title: String) {
    data object MainPage : Route("main_page")
    data object PersonaScreen : Route("persona_screen")
    data object SettingScreen : Route("Setting Screen")
    data object FavoriteScreen : Route("Favorite Screen")
}
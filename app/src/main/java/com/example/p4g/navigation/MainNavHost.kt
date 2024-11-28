package com.example.p4g.navigation
import android.net.Uri
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.p4g.MainContent
import com.example.p4g.Screen.FavoritesScreen
import com.example.p4g.Screen.KarakterScreen
import com.example.p4g.Screen.SettingScreen
import com.example.p4g.datastore.saveFavorites
import com.example.p4g.model.Persona
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


@Composable
fun MainNavHost(
    navController: NavHostController,
    onRouteChanged: (Route) -> Unit,
    modifier: Modifier = Modifier,
    favorites: MutableList<Persona>,
    onSaveFavorites: (List<Persona>) -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = Route.MainPage.title,
        modifier = modifier,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None }
    ) {
        // Main Page Route
        composable(Route.MainPage.title) {
            onRouteChanged(Route.MainPage)
            // Pass navigation logic for persona screen
            MainContent(
                onNavigateToPersonaScreen = { listItem ->
                    val pJson = Uri.encode(Json.encodeToString(listItem))
                    navController.navigate("${Route.PersonaScreen.title}/$pJson")
                }
            )
        }

        // Persona Details Route
        composable(
            "${Route.PersonaScreen.title}/{pJson}",
            enterTransition = {
                slideInHorizontally(initialOffsetX = { 1000 }) + fadeIn() // Slide in from the right
            },
            exitTransition = {
                slideOutHorizontally(targetOffsetX = { 1000 }) + fadeOut() // Slide out to the left
            }
        ) { backStackEntry ->
            val personaJson = backStackEntry.arguments?.getString("pJson") // Fetch entire persona from arguments
            val persona = personaJson?.let {Json.decodeFromString<Persona>(Uri.decode(it))}
            var isInFav = false

            for (favouritePersona in favorites) {
                if (favouritePersona.name == persona?.name) {
                    isInFav = true
                }
            }

            KarakterScreen(
                persona = persona,
                onFavoriteClick = {
                    if (persona != null) {
                        var i = 0
                        for (favouritePersona in favorites) {
                            if (favouritePersona.name == persona.name) {
                                break
                            }
                            i++
                        }
                        if (i >= favorites.size) {
                            favorites.add(persona)
                        }
                        else {
                            favorites.removeAt(i)
                        }
                        onSaveFavorites(favorites.toList())
                    }
                },
                personaExistsInFavourites = isInFav
            )
        }

        composable(Route.SettingScreen.title){
            onRouteChanged(Route.SettingScreen)
            SettingScreen(
                onClearFavorites = {
                    favorites.clear() // Clear favorites list
                    onSaveFavorites( favorites.toList()) // Save the empty list to DataStore
                }
            )

        }

        composable(Route.FavoriteScreen.title){
            onRouteChanged(Route.FavoriteScreen)
            FavoritesScreen(
                favorites = favorites,
                onNavigateToPersona = { persona ->
                    val personaJson = Uri.encode(Json.encodeToString(persona))
                    navController.navigate("${Route.PersonaScreen.title}/$personaJson")
                }
            )

        }
    }
}




package com.example.p4g.navigation
import android.net.Uri
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.p4g.MainContent
import com.example.p4g.Screen.KarakterScreen
import com.example.p4g.Screen.SettingScreen
import com.example.p4g.Screen.FavoritesScreen
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
        composable("${Route.PersonaScreen.title}/{pJson}") { backStackEntry ->
            val personaJson = backStackEntry.arguments?.getString("pJson") // Fetch name from arguments
            val persona = personaJson?.let {Json.decodeFromString<Persona>(Uri.decode(it))}
            KarakterScreen(
                persona = persona, onNavigateBack = { navController.popBackStack() },
                onFavoriteClick = { selectedPersona ->
                    if (selectedPersona != null) {
                        if (favorites.contains(selectedPersona)) {
                            favorites.remove(selectedPersona)
                        } else {
                            favorites.add(selectedPersona)
                        }
                        onSaveFavorites(favorites.toList())
                    }
                }
            )
        }

        composable(Route.SettingScreen.title){
            onRouteChanged(Route.SettingScreen)
            SettingScreen()

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




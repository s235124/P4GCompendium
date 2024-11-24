package com.example.p4g.navigation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.p4g.Screen.PersonaScreen
import com.example.p4g.MainContent


@Composable
fun MainNavHost(
    navController: NavHostController,
    onRouteChanged: (Route) -> Unit,
    modifier: Modifier = Modifier
) {
    androidx.navigation.compose.NavHost(
        navController = navController,
        startDestination = Route.MainPage.title,
        modifier = modifier
    ) {
        // Main Page Route
        composable(Route.MainPage.title) {
            onRouteChanged(Route.MainPage)
            // Pass navigation logic for persona screen
            MainContent(
                onNavigateToPersonaScreen = { listItem ->
                    navController.navigate("${Route.PersonaScreen.title}/${listItem.name}")
                }
            )
        }

        // Persona Details Route
        composable("${Route.PersonaScreen.title}/{name}") { backStackEntry ->
            val name = backStackEntry.arguments?.getString("name") // Fetch name from arguments
            PersonaScreen(name = name, onNavigateBack = { navController.popBackStack() })
        }
    }
}




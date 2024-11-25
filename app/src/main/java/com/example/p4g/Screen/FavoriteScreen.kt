package com.example.p4g.Screen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.p4g.model.Persona

@Composable
fun FavoriteScreen() {
    Text(text = "Favorite")

}


/*@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarWithFavorite(
    persona: Persona?,
    onNavigateBack: () -> Unit,
    onFavoriteClick: (Persona?) -> Unit
) {
    androidx.compose.material3.TopAppBar(
        title = { Text(text = persona?.name ?: "Details") },
        navigationIcon = {
            IconButton(onClick = onNavigateBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }
        },
        actions = {
            IconButton(onClick = { onFavoriteClick(persona) }) {
                Icon(
                    imageVector = Icons.Filled.Favorite,
                    contentDescription = "Favorite"
                )
            }
        }
    )
}*/

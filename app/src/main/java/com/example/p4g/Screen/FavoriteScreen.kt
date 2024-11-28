package com.example.p4g.Screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.p4g.ListCard
import com.example.p4g.model.Persona

@Composable
fun FavoritesScreen(
    favorites: List<Persona>,
    onNavigateToPersona: (Persona) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(favorites) { persona ->
            ListCard(
                persona = persona,
                onClick = { onNavigateToPersona(persona) }
            )
        }
    }
}





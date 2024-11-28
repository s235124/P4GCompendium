package com.example.p4g.Screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier

@Composable
fun SettingScreen(
    onClearFavorites: () -> Unit
) {
    val showDialog = remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            // Flyt teksten længere op
            Spacer(modifier = Modifier.height(32.dp)) // Plads øverst
            Text(
                text = "Settings",
                style = androidx.compose.material3.MaterialTheme.typography.headlineLarge
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Knap til at rydde favoritter
            Button(
                onClick = { showDialog.value = true }
            ) {
                Text("Clear All Favorites")
            }
        }
    }

    // Dialog til bekræftelse
    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = { showDialog.value = false },
            title = {
                Text(text = "Confirm Action")
            },
            text = {
                Text(text = "Are you sure you want to clear all favorites?")
            },
            confirmButton = {
                Button(
                    onClick = {
                        showDialog.value = false
                        onClearFavorites()
                    }
                ) {
                    Text("Yes")
                }
            },
            dismissButton = {
                Button(
                    onClick = { showDialog.value = false }
                ) {
                    Text("No")
                }
            }
        )
    }
}
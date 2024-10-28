package com.example.p4g

import android.os.Bundle
import android.view.Window
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.p4g.ui.theme.P4GTheme

private data class Persona (
    val id: Int,
    val name: String,
    val level: Int,
    val arcana: String,
    val primaryElement: String,
    val elements: MutableList<String>
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            P4GTheme {
                P4GCompendiumApp()
            }
        }
    }
}

@Preview
@Composable
fun P4GCompendiumApp () {
    MainPage()
}

@Composable
fun MainPage (modifier: Modifier = Modifier
    .fillMaxSize()
    .wrapContentSize()
    .height(20.dp)
) {
    BottomAppBarExample()
}

@Composable
fun SearchBar(modifier: Modifier = Modifier) {

    var searchText by remember { mutableStateOf(TextFieldValue("")) }

    Box(
        modifier = modifier
            .width(360.dp)
            .background(color = Color.LightGray, shape = RoundedCornerShape(30.dp))
            .height(50.dp)
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.CenterStart

    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth().align(Alignment.Center).fillMaxHeight()
        ) {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = "Search",
                modifier = Modifier.size(24.dp),
                tint = Color.DarkGray
            )

            Spacer(modifier = Modifier.width(8.dp))

            TextField(
                value = searchText,  // Bind TextField to state
                onValueChange = { newText ->
                    searchText = newText  // Update state when the user types
                    // Optionally, trigger search logic here
                },
                placeholder = {
                    Text(
                        text = "Search", color = Color.DarkGray,


                    )
                },
                //singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedTextColor = Color.Black,             // Text color when focused
                    unfocusedTextColor = Color.Black,           // Text color when not focused
                    focusedPlaceholderColor = Color.DarkGray,       // Placeholder when focused
                    unfocusedPlaceholderColor = Color.DarkGray,     // Placeholder when not focused
                    focusedContainerColor = Color.Transparent,  // Transparent background
                    unfocusedContainerColor = Color.Transparent, // Transparent background
                    cursorColor = Color.Black,                  // Black cursor
                    focusedIndicatorColor = Color.Transparent,   // Remove underline when focused
                    unfocusedIndicatorColor = Color.Transparent  // Remove underline when not focused


                )

            )

        }
    }
}

@Composable
fun BottomAppBarExample() {
    Column(modifier = Modifier.padding(WindowInsets.statusBars.asPaddingValues())) {
        Scaffold(
            topBar = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp),  // Add padding to avoid the status bar
                    horizontalArrangement = Arrangement.Center // Center the SearchBar horizontally
                ) {
                    SearchBar()  // This SearchBar is now centered
                }
            },
            bottomBar = {
                BottomAppBar {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            onClick = { /* do something */ },
                            modifier = Modifier.padding(horizontal = 8.dp)
                        ) {
                            Icon(
                                Icons.Filled.Home,
                                contentDescription = "Localized description",
                                modifier = Modifier.size(32.dp)
                            )
                        }
                        Spacer(modifier = Modifier.weight(1f))
                        IconButton(
                            onClick = { /* do something */ },
                            modifier = Modifier.padding(horizontal = 8.dp)
                        ) {
                            Icon(
                                Icons.Filled.Favorite,
                                contentDescription = "Localized description",
                                modifier = Modifier.size(32.dp)
                            )
                        }
                        Spacer(modifier = Modifier.weight(1f))
                        IconButton(
                            onClick = { /* do something */ },
                            modifier = Modifier.padding(horizontal = 8.dp)
                        ) {
                            Icon(
                                Icons.Filled.Settings,
                                contentDescription = "Localized description",
                                modifier = Modifier.size(32.dp)
                            )
                        }
                    }
                }
            },
            content = { innerPadding ->
                Column(
                    modifier = Modifier
                        .padding(innerPadding)
                        .padding(10.dp)

                ) {

                }
            }
        )
    }
}
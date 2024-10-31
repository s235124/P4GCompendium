package com.example.p4g

import android.os.Bundle
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.example.p4g.listItems.Listitem
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
fun ListCard(listitem: Listitem, modifier: Modifier = Modifier) {
    // Calculate % of screen height
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val cardHeight = screenHeight * 0.1f

    Card(modifier = modifier.height(cardHeight)) {
        Row {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(0.4f) // Adjust the weight as needed for the image
                    .clip(shape = TriangleShape()) // Use a custom shape for the angled cut
            ) {
                Image(
                    painter = painterResource(listitem.img),
                    contentDescription = stringResource(listitem.name),
                    modifier = Modifier.padding(10.dp)
                        .fillMaxSize()
                        .clip(shape = TriangleShape()), // Ensure the image fits the angled shape
                    contentScale = ContentScale.FillWidth
                )
            }
            Column(
                modifier = Modifier
                    .weight(0.6f) // Adjust the weight for the text
                    .padding(16.dp)
                    .fillMaxHeight()
            ) {
                Text(
                    text = LocalContext.current.getString(listitem.name),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

// Custom shape for the angled cut
class TriangleShape : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val path = Path().apply {
            moveTo(0f, 0f)
            lineTo(size.width, 0f)
            lineTo(size.width, size.height)
            lineTo(0f, size.height)
            close()
        }
        return Outline.Generic(path)
    }
}


@Preview
@Composable
private fun ListCardPreview() {
    ListCard(Listitem(R.string.v,R.drawable.i_prc0b0_tmx_1))
}

@Composable
fun ListItemList(modifier: Modifier = Modifier, searchQuery: String) {

    val listItemList = List(10) {
        Listitem(R.string.v, R.drawable.i_prc0b0_tmx_1) // Replace with your actual resources
        Listitem(R.string.v, R.drawable.i_prc0b0_tmx_1)
        Listitem(R.string.v, R.drawable.i_prc0b0_tmx_1)
        Listitem(R.string.v, R.drawable.i_prc0b0_tmx_1)
        Listitem(R.string.v, R.drawable.i_prc0b0_tmx_1)
        Listitem(R.string.v, R.drawable.i_prc0b0_tmx_1) // Replace with your actual resources
        Listitem(R.string.v, R.drawable.i_prc0b0_tmx_1)
        Listitem(R.string.v, R.drawable.i_prc0b0_tmx_1)
        Listitem(R.string.v, R.drawable.i_prc0b0_tmx_1)
        Listitem(R.string.v, R.drawable.i_prc0b0_tmx_1)
    }

    val filtered = listItemList.filter {
        LocalContext.current.getString(it.name).contains(searchQuery, ignoreCase = true)
    }

    LazyColumn(modifier = modifier) {
        items(filtered) { listItem ->
            ListCard(
                listitem = listItem,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}



@Composable
fun SearchBar(modifier: Modifier = Modifier, onSearchTextChange: (String) -> Unit, searchText: String) {


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
                onValueChange = onSearchTextChange,
                placeholder = {
                    Text(
                        text = "Search", color = Color.DarkGray
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
    var searchText by remember { mutableStateOf("") }
    Column(modifier = Modifier.padding(WindowInsets.statusBars.asPaddingValues())) {
        Scaffold(
            topBar = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp),  // Add padding to avoid the status bar
                    horizontalArrangement = Arrangement.Center // Center the SearchBar horizontally
                ) {
                    SearchBar(
                        searchText = searchText,
                        onSearchTextChange = {
                            newText -> searchText = newText
                        }
                    )  // This SearchBar is now centered
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
                    ListItemList(searchQuery = searchText)
                }
            }
        )
    }
}
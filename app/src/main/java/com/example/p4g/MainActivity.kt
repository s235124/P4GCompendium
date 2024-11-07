package com.example.p4g

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.example.p4g.HTTP.PersonaViewModel
import com.example.p4g.listItems.ListItem
import com.example.p4g.ui.theme.P4GTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext


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
fun MainPage(modifier: Modifier = Modifier) {
    var searchText by remember { mutableStateOf(TextFieldValue("")) }
    var personaList by remember { mutableStateOf<List<Persona>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }

    // Fetch persona list
    val originalList = fetchList(modifier = Modifier, personaViewModel = PersonaViewModel())

    // Once fetched, update the persona list and set loading to false
    LaunchedEffect(originalList) {
        if (originalList.isNotEmpty()) {
            personaList = originalList
            isLoading = false
        }
    }

    // Filtering logic
    val filteredList = personaList.filter {
        it.name.contains(searchText.text, ignoreCase = true)
    }

    BottomAppBarExample(
        searchText = searchText,
        onSearchTextChange = { newText -> searchText = newText },
        filteredList = filteredList,
        isLoading = isLoading
    )
}

@Composable
fun BottomAppBarExample(
    searchText: TextFieldValue,
    onSearchTextChange: (TextFieldValue) -> Unit,
    filteredList: List<Persona>,
    isLoading: Boolean
) {
    Column(modifier = Modifier.padding(WindowInsets.statusBars.asPaddingValues())) {
        Scaffold(
            topBar = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    SearchBar(searchText = searchText, onSearchTextChange = onSearchTextChange)
                }
            },
            bottomBar = { BottomBar(modifier = Modifier.fillMaxWidth()) },
            content = { innerPadding ->
                Column(
                    modifier = Modifier
                        .padding(innerPadding)
                        .padding(10.dp)
                ) {
                    // Pass filteredList and loading state to ListItemList
                    ListItemList(modifier = Modifier, filteredList = filteredList, isLoading = isLoading)
                }
            }
        )
    }
}

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

@Composable
fun ListCard(listitem: ListItem, modifier: Modifier = Modifier) {
    // Calculate % of screen height
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val cardHeight = screenHeight * 0.1f

    Card(modifier = modifier.height(cardHeight)) {
        Row (modifier = Modifier.clickable(
                onClick = { /* go to specific personas page */ },
                indication = rememberRipple(bounded = true), // Ripple effect for feedback
                interactionSource = remember { MutableInteractionSource() }
                )) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(0.3f)
                    .clip(shape = TriangleShape())
            ) {
                Image(
                    painter = painterResource(listitem.img),
                    contentDescription = listitem.name,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(shape = TriangleShape()), // Ensure the image fits the angled shape
                    contentScale = ContentScale.FillWidth
                )
            }
            Column(
                modifier = Modifier
                    .weight(0.6f)
                    .padding(16.dp)
                    .fillMaxHeight()
            ) {
                Text(
                    text = listitem.name,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            Column(
                modifier = Modifier
                    .weight(0.3f)
                    .padding(16.dp)
                    .fillMaxHeight()
            ) {
                Text(
                    text = listitem.level.toString(),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            Column(
                modifier = Modifier
                    .weight(0.6f)
                    .padding(16.dp)
                    .fillMaxHeight()
            ) {
                Text(
                    text = listitem.race,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

@Preview
@Composable
private fun ListCardPreview() {
//    ListCard(ListItem("R.string.v",R.drawable.i_prc0b0_tmx_1))
}

@Composable
fun fetchList(modifier: Modifier, personaViewModel: PersonaViewModel): List<Persona> {
    var personaList by remember { mutableStateOf<List<Persona>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        withContext(Dispatchers.IO) {
            try {
                Log.d("ListItemList", "Fetching data...")
                delay(1000) // Simulate network delay for testing
                personaList = PersonaJSON.makeList(personaViewModel) // Pass the ViewModel
                Log.d("ListItemList", "Data fetched: ${personaList.size} items") // Log the size of the list
            } catch (e: Exception) {
                Log.e("ListItemList", "Error fetching data: ${e.message}")
            } finally {
                isLoading = false // Ensure loading state is updated
            }
        }
    }

    // Return the fetched list even if it's empty while loading
    return personaList
}

@Composable
fun ListItemList(modifier: Modifier = Modifier, filteredList: List<Persona>, isLoading: Boolean) {
    if (isLoading) {
        Box(modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    } else {
        LazyColumn(modifier = modifier) {
            items(filteredList) { listItem ->
                Log.d("ListItemList", "Rendering item: ${listItem.name}")
                val lItem = ListItem(listItem.name, R.drawable.i_prc0b0_tmx_1, listItem.level, listItem.race)
                ListCard(
                    listitem = lItem,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}

@Composable
fun BottomBar (modifier: Modifier) {
    BottomAppBar {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { /* Do something */ },
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
}

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    searchText: TextFieldValue = TextFieldValue(""),
    onSearchTextChange: (TextFieldValue) -> Unit
) {
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
                value = searchText,
                onValueChange = { newText ->
                    onSearchTextChange(newText) // Call the passed function to handle text changes
                },
                placeholder = {
                    Text(text = "Search", color = Color.DarkGray)
                },
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    focusedPlaceholderColor = Color.DarkGray,
                    unfocusedPlaceholderColor = Color.DarkGray,
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    cursorColor = Color.Black,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )
        }
    }
}


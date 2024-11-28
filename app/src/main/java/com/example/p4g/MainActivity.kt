package com.example.p4g

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.p4g.HTTP.PersonaViewModel
import com.example.p4g.datastore.getFavorites
import com.example.p4g.datastore.saveFavorites
import com.example.p4g.model.ListItem
import com.example.p4g.model.Persona
import com.example.p4g.navigation.MainNavHost
import com.example.p4g.navigation.Route
import com.example.p4g.ui.theme.P4GTheme
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

const val GOLDEN_COLOR = 0xFFFFe52C

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

@Composable
fun P4GCompendiumApp() {
    val navController = rememberNavController()

    MainPage(
        navController = navController,
        onRouteChanged = { route ->
            Log.d("P4GCompendiumApp", "Navigated to route: ${route.title}")
        },
        modifier = Modifier.fillMaxSize()
    )
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainPage(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    onRouteChanged: (Route) -> Unit
) {
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val canNavigateBack = currentBackStackEntry?.destination?.route?.startsWith("persona_screen") == true
    val favorites = remember { mutableStateListOf<Persona>() }
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope() // Coroutine scope for saving favorites
    val currentPageName = currentBackStackEntry?.destination?.route

    // Load favorites from DataStore
    LaunchedEffect(Unit) {
        getFavorites(context).collectLatest { savedFavorites ->
            favorites.clear()
            favorites.addAll(savedFavorites)
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "P4G Compendium",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(8.dp),
                        textAlign = TextAlign.Center
                    )
                },
                navigationIcon = {
                    if (canNavigateBack) {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = null
                            )
                        }
                    }
                }
            )
        },
        bottomBar = { BottomBar(
            modifier = Modifier.fillMaxWidth(),
            onHomeClick = {
                navController.navigate(Route.MainPage.title) {
                    launchSingleTop = true
                }
            },
            onSettingsClick = {
                navController.navigate(Route.SettingScreen.title) {
                    launchSingleTop = true
                }
            },
            onFavoriteClick = {
                navController.navigate(Route.FavoriteScreen.title) {
                    launchSingleTop = true
                }
            },
            currentTab = currentPageName
        ) },
        content = { innerPadding ->
            // Embed the navigation host within the page
            MainNavHost(
                navController = navController,
                favorites = favorites,
                onRouteChanged = onRouteChanged,
                onSaveFavorites = { updatedFavorites ->
                    // Launch coroutine to save favorites
                    coroutineScope.launch {
                        saveFavorites(context, updatedFavorites)
                    }
                },
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            )
        }
    )
}



@Composable
fun MainContent(
    onNavigateToPersonaScreen: (Persona) -> Unit
) {
    var searchText by remember { mutableStateOf(TextFieldValue("")) }
    var personaList by remember { mutableStateOf<List<Persona>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }

    // Fetch persona list
    val originalList = fetchList(modifier = Modifier)

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

    Column(modifier = Modifier.padding(10.dp).fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        SearchBar(searchText = searchText, onSearchTextChange = { newText -> searchText = newText })

        ListItemList(
            modifier = Modifier.fillMaxSize(),
            filteredList = filteredList,
            isLoading = isLoading,
            onCardClick = { onNavigateToPersonaScreen(it) }
        )
    }
}


@Composable
fun ListCard(persona: Persona, modifier: Modifier = Modifier, onClick: (Persona) -> Unit) {
    val listItem = ListItem(persona)
    // Calculate % of screen height
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val cardHeight = screenHeight * 0.1f

    Card(modifier = modifier.height(cardHeight)) {
        Row(
            modifier = Modifier.clickable(
                onClick = { onClick(persona) },
                indication = rememberRipple(bounded = true), // Ripple effect for feedback
                interactionSource = remember { MutableInteractionSource() }
            )
                .background(color = Color(GOLDEN_COLOR))
        ) {
            // Image container
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(0.3f)
                    .clip(shape = RoundedCornerShape(100))
            ) {
                Image(
                    painter = painterResource(listItem.img),
                    contentDescription = listItem.name,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(shape = RoundedCornerShape(1)), // Ensure the image fits the angled shape
                    contentScale = ContentScale.FillWidth
                )
            }

            // Main text container
            Column(
                modifier = Modifier
                    .weight(0.7f) // Adjust the weight as needed
                    .padding(16.dp)
                    .fillMaxHeight()
            ) {
                // First text spanning the full width
                Box (
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(0.3f)
                        .background(color = Color.Yellow)
                        .border(1.dp, Color.Black)
                ){
                    Text(
                        text = listItem.name,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.fillMaxWidth(),
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center
                    )
                }

                // Row for the other two texts
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp) // Space between name and other texts
                ) {
                    // Second text (level)
                    Text(
                        text = listItem.level.toString(),
                        style = MaterialTheme.typography.bodyLarge,
                        fontSize = 18.sp,
                        modifier = Modifier
                            .weight(1f)
                            .background(color = Color.Yellow)
                            .border(1.dp, Color.Black),
                        textAlign = TextAlign.Center
                    )

                    // Third text (race)
                    Text(
                        text = listItem.race,
                        style = MaterialTheme.typography.bodyLarge,
                        fontSize = 18.sp,
                        modifier = Modifier
                            .weight(1f)
                            .background(color = Color.Yellow)
                            .border(1.dp, Color.Black),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }

}

//@Composable
//fun fetchList(modifier: Modifier): List<Persona> {
//    var personaList by remember { mutableStateOf<List<Persona>>(emptyList()) }
//    var isLoading by remember { mutableStateOf(true) }
//
//    LaunchedEffect(Unit) {
//        withContext(Dispatchers.IO) {
//            while (personaList.isEmpty()) {
//                try {
//                    Log.d("ListItemList", "Fetching data...")
//                    val pvm = PersonaViewModel()
//                    delay(2000) // Simulate network delay for testing
//                    personaList = PersonaJSON.makeList(pvm) // Pass the ViewModel
//                    Log.d("ListItemList", "Data fetched: ${personaList.size} items") // Log the size of the list
//                } catch (e: Exception) {
//                    Log.e("ListItemList", "Error fetching data: ${e.message}")
//                } finally {
//                    isLoading = false // Ensure loading state is updated
//                }
//            }
//        }
//    }
//
//    // Return the fetched list even if it's empty while loading
//    return personaList
//}

@Composable
fun fetchList(modifier: Modifier = Modifier): List<Persona> {
    val viewModel: PersonaViewModel = viewModel() // Proper ViewModel instantiation
    val personas by viewModel.personas.collectAsState(initial = null) // Collect StateFlow

    return PersonaJSON.makeList(personas)
}


@SuppressLint("DiscouragedApi")
@Composable
fun ListItemList(
    modifier: Modifier = Modifier,
    filteredList: List<Persona>,
    isLoading: Boolean,
    onCardClick: (Persona) -> Unit
) {
    val raceOrder = listOf(
        "Fool", "Magician", "Priestess", "Empress", "Emperor",
        "Hierophant", "Lovers", "Chariot", "Justice", "Hermit",
        "Fortune", "Strength", "Hanged", "Death", "Temperance",
        "Devil", "Tower", "Star", "Moon", "Sun", "Judgement",
        "Aeon", "Jester", "World"
    )

    // Sort JSON objects
    val sortedList = filteredList.sortedWith(
        compareBy(
            { raceOrder.indexOf(it.race) }, // Sort by race order
            { -it.level } // Then by level descending
        )
    )
    if (isLoading) {
        Box(modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    } else {
        LazyColumn(modifier = modifier) {
            // A bit of an unorthodox way of getting images for cards, but alas, a way
            var i = 1
            items(sortedList) { listItem ->
//                Log.d("ListItemList", "Rendering item: ${listItem.name}")

                val fetchableName = listItem.name
                    .replace(' ','_')
                    .replace('-', '_')
                    .lowercase()

                // Get the drawable resource ID using the name
                val context = LocalContext.current
                val drawableName = fetchableName // Ensure the name matches the drawable resource format
                val drawableResId = context.resources.getIdentifier(
                    drawableName,
                    "drawable",
                    context.packageName
                )

                // Set a default drawable if the resource ID is not found
                val finalDrawable = if (drawableResId != 0) drawableResId else R.drawable.i_prc0b0_tmx_1

                listItem.img = finalDrawable

//                val lItem = ListItem(listItem.name, finalDrawable, listItem.level, listItem.race)
                ListCard(
                    persona = listItem,
                    modifier = Modifier.padding(8.dp),
                    onClick = onCardClick
                )

                i++
            }
        }
    }
}

@Composable
fun BottomBar (modifier: Modifier, onHomeClick: () -> Unit, onSettingsClick: () -> Unit, onFavoriteClick: () -> Unit, currentTab: String?) {
    BottomAppBar {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onHomeClick,
                modifier = Modifier.padding(horizontal = 8.dp)
            ) {
                val icon = if (currentTab == null || currentTab == "main_page" || currentTab.startsWith("persona_screen")) Icons.Filled.Home else Icons.Outlined.Home
                Icon(
                    imageVector = icon,
                    contentDescription = "Home",
                    modifier = Modifier.size(32.dp)
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            IconButton(
                onClick = onFavoriteClick,
                modifier = Modifier.padding(horizontal = 8.dp)
            ) {
                val icon = if (currentTab == "Favorite Screen") Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder
                Icon(
                    imageVector = icon,
                    contentDescription = "Favorite",
                    modifier = Modifier.size(32.dp)
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            IconButton(
                onClick = onSettingsClick,
                modifier = Modifier.padding(horizontal = 8.dp)
            ) {
                val icon = if (currentTab == "Setting Screen") Icons.Filled.Settings else Icons.Outlined.Settings
                Icon(
                    imageVector = icon,
                    contentDescription = "Setting",
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


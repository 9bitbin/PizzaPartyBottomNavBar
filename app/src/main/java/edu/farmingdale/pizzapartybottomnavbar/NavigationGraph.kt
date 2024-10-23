package edu.farmingdale.pizzapartybottomnavbar

/**
 * Name: Himal Shrestha
 * Class: Mobile Application Development
 * Prof: Alrajab
 */

// Importing necessary Compose, Navigation, and Material3 libraries
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import kotlinx.coroutines.launch

// ToDo 8: This is the homework:
// add a drawer navigation as described in drawable drawermenu.png
// Improve the design and integration of the app for 5 extra credit points.

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationGraph(
    navController: NavHostController, // Navigation controller used to manage app navigation
    onBottomBarVisibilityChanged: (Boolean) -> Unit, // Function to control visibility of bottom bar
) {
    val drawerState =
        rememberDrawerState(DrawerValue.Closed) // Keeps track of drawer state (opened or closed)
    val coroutineScope =
        rememberCoroutineScope() // Provides coroutine scope to handle asynchronous tasks

    // Drawer layout that includes navigation drawer
    ModalNavigationDrawer(
        drawerState = drawerState, // Passes the drawer state to manage drawer open/close status
        drawerContent = {
            ModalDrawerSheet {
                // Drawer items for different screens
                NavigationDrawerItem(
                    icon = { Icon(Icons.Default.ShoppingCart, contentDescription = "Cart Icon") },
                    label = { Text("Pizza Order") }, // Label for the item in the drawer
                    selected = false, // Whether the item is currently selected
                    onClick = {
                        coroutineScope.launch {
                            drawerState.close() // Closes drawer when item is clicked
                            navController.navigate(BottomNavigationItems.PizzaScreen.route) // Navigates to PizzaScreen
                        }
                    }
                )

                NavigationDrawerItem(
                    icon = { Icon(Icons.Default.Info, contentDescription = "GPA Icon") },
                    label = { Text("GPA App") }, // Label for GPA App item
                    selected = false,
                    onClick = {
                        coroutineScope.launch {
                            drawerState.close() // Closes drawer
                            navController.navigate(BottomNavigationItems.GpaAppScreen.route) // Navigates to GpaAppScreen
                        }
                    }
                )

                NavigationDrawerItem(
                    icon = {
                        Icon(
                            Icons.Default.AccountCircle,
                            contentDescription = "Profile Icon"
                        )
                    },
                    label = { Text("Screen3") }, // Label for Screen3 item
                    selected = false,
                    onClick = {
                        coroutineScope.launch {
                            drawerState.close() // Closes drawer
                            navController.navigate(BottomNavigationItems.Screen3.route) // Navigates to Screen3
                        }
                    }
                )
            }
        }
    )
    {
        // The main scaffold of the app that includes top app bar and content area
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("") }, // Empty title for the top app bar
                    navigationIcon = {
                        IconButton(onClick = {
                            coroutineScope.launch {
                                drawerState.open() // Opens the navigation drawer when menu icon is clicked
                            }
                        }) {
                            Icon(
                                Icons.Default.Menu,
                                contentDescription = "Menu"
                            ) // Menu icon to open the drawer
                        }
                    }
                )
            },
            content = { padding ->
                // Content area where the navigation host manages different composable screens
                Box(
                    modifier = Modifier
                        .fillMaxSize() // Fills the maximum size available
                        .padding(padding) // Adds padding to avoid overlap with the app bar
                ) {
                    NavHost(
                        navController = navController, // Manages navigation between different screens
                        startDestination = BottomNavigationItems.Welcome.route // Default screen to load when app starts
                    ) {
                        composable(BottomNavigationItems.Welcome.route) {
                            onBottomBarVisibilityChanged(false) // Controls visibility of bottom bar
                           SplashScreen(navController = navController) // Displays the SplashScreen
                        }

                        // Composable for Pizza screen
                        composable(BottomNavigationItems.PizzaScreen.route) {
                            onBottomBarVisibilityChanged(false) // Controls visibility of bottom bar
                            PizzaPartyScreen() // Displays the PizzaPartyScreen
                        }
                        // Composable for GPA App screen
                        composable(BottomNavigationItems.GpaAppScreen.route) {
                            onBottomBarVisibilityChanged(false)
                            GpaAppScreen() // Displays the GpaAppScreen
                        }
                        // Composable for Screen 3
                        composable(BottomNavigationItems.Screen3.route) {
                            onBottomBarVisibilityChanged(false)
                            Screen3() // Displays Screen3
                        }
                    }
                }
            }
        )
    }
}// End Class File

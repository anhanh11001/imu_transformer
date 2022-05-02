package ducletran.tech.imutransformer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import ducletran.tech.imutransformer.ui.components.FullScreenText
import ducletran.tech.imutransformer.ui.navigation.BottomTab
import ducletran.tech.imutransformer.ui.theme.IMUTransformerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            IMUTransformerTheme {
                val navController = rememberNavController()
                Scaffold(
                    bottomBar = {
                        BottomNavigation {
                            val navBackStackEntry by navController.currentBackStackEntryAsState()
                            val currentDestination = navBackStackEntry?.destination
                            BottomTab.items.forEach { tab ->
                                BottomNavigationItem(
                                    icon = {
                                        Icon(
                                            painter = painterResource(id = tab.resourceId),
                                            contentDescription = null
                                        )
                                    },
                                    label = { Text(stringResource(tab.descriptionId)) },
                                    selected = currentDestination?.hierarchy?.any { it.route == tab.route } == true,
                                    onClick = {
                                        navController.navigate(tab.route) {
                                            // Pop up to the start destination of the graph to
                                            // avoid building up a large stack of destinations
                                            // on the back stack as users select items
                                            popUpTo(navController.graph.findStartDestination().id) {
                                                saveState = true
                                            }
                                            // Avoid multiple copies of the same destination when
                                            // reselecting the same item
                                            launchSingleTop = true
                                            // Restore state when reselecting a previously selected item
                                            restoreState = true
                                        }
                                    }
                                )
                            }
                        }
                    }
                ) { innerPadding ->
                    NavHost(
                        navController,
                        startDestination = BottomTab.Data.route,
                        Modifier.padding(innerPadding)
                    ) {
                        composable(BottomTab.Data.route) {
                            FullScreenText(text = "Data Screen")
                        }
                        composable(BottomTab.Label.route) {
                            FullScreenText(text = "Label Screen")
                        }
                        composable(BottomTab.Model.route) {
                            FullScreenText(text = "AI Model Deployment Screen")
                        }
                    }
                }
            }
        }
    }
}

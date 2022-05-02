package ducletran.tech.imutransformer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
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
import ducletran.tech.imutransformer.ui.datacollection.ExperimentListScreenWithNav
import ducletran.tech.imutransformer.ui.label.CreateLabelScreenMain
import ducletran.tech.imutransformer.ui.label.LabelListScreenWithNav
import ducletran.tech.imutransformer.ui.model.IntelligenceModelScreenWithNav
import ducletran.tech.imutransformer.ui.navigation.BottomTab
import ducletran.tech.imutransformer.ui.navigation.IMUScreen
import ducletran.tech.imutransformer.ui.theme.IMUTransformerTheme
import timber.log.Timber

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.d("Entering screen by initializing BottomTab items: ${BottomTab.items} DO NOT REMOVE THIS LINE")

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
                                    label = {
                                        Text(stringResource(tab.descriptionId))
                                    },
                                    selected = currentDestination?.hierarchy?.any { it.route == tab.route } == true,
                                    onClick = {
                                        navController.navigate(tab.route) {
                                            popUpTo(navController.graph.findStartDestination().id) {
                                                saveState = true
                                            }
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                    }
                                )
                            }
                        }
                    },
                    floatingActionButtonPosition = FabPosition.End,
                    isFloatingActionButtonDocked = false,
                    floatingActionButton = {
                        val supportedRoutes = setOf(
                            BottomTab.Data.route,
                            BottomTab.Label.route
                        )
                        FloatingActionButton(
                            shape = CircleShape,
                            onClick = {
                                val currentRoute = navController
                                    .currentBackStackEntry
                                    ?.destination
                                    ?.route
                                when (currentRoute) {
                                    BottomTab.Data.route -> {
                                        navController.navigate(IMUScreen.CreateExperiment.route)
                                    }
                                    BottomTab.Label.route -> {
                                        navController.navigate(IMUScreen.CreateLabel.route)
                                    }
                                    else -> { // Do nothing
                                    }
                                }
                            }
                        ) {
                            Icon(Icons.Rounded.Add, null)
                        }
                    }
                ) { innerPadding ->
                    NavHost(
                        navController,
                        startDestination = BottomTab.Data.route,
                        Modifier.padding(innerPadding)
                    ) {
                        composable(BottomTab.Data.route) {
                            ExperimentListScreenWithNav(navController = navController)
                        }
                        composable(BottomTab.Label.route) {
                            LabelListScreenWithNav(navController = navController)
                        }
                        composable(BottomTab.Model.route) {
                            IntelligenceModelScreenWithNav(navController = navController)
                        }
                        composable(IMUScreen.CreateLabel.route) {
                            CreateLabelScreenMain(navController = navController)
                        }
                        composable(IMUScreen.CreateExperiment.route) {
                            FullScreenText(text = "Create an experiment")
                        }
                    }
                }
            }
        }
    }
}

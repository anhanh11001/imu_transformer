package ducletran.tech.imutransformer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.Add
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dagger.hilt.android.AndroidEntryPoint
import ducletran.tech.imutransformer.ui.components.FullScreenText
import ducletran.tech.imutransformer.ui.datacollection.ExperimentListScreenWithNav
import ducletran.tech.imutransformer.ui.datacollection.ExperimentScreenWithNav
import ducletran.tech.imutransformer.ui.label.CreateLabelScreenMain
import ducletran.tech.imutransformer.ui.label.LabelListScreenWithNav
import ducletran.tech.imutransformer.ui.ai.IntelligenceModelScreenWithNav
import ducletran.tech.imutransformer.ui.datacollection.CustomExperimentSetupScreenWithNav
import ducletran.tech.imutransformer.ui.navigation.BottomTab
import ducletran.tech.imutransformer.ui.navigation.IMUScreen
import ducletran.tech.imutransformer.ui.theme.IMUTransformerTheme
import ducletran.tech.imutransformer.ui.utils.SlotLayoutData
import timber.log.Timber


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.d("Entering screen by initializing BottomTab items: ${BottomTab.items} DO NOT REMOVE THIS LINE")

        setContent {
            IMUTransformerTheme {
                val navController = rememberNavController()
                var layoutData by rememberSaveable { mutableStateOf(SlotLayoutData("IMU App Data")) }

                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text(text = layoutData.text) },
                            navigationIcon = if (layoutData.isBackButtonEnabled) {
                                {
                                    IconButton(onClick = { navController.popBackStack() }) {
                                        Icon(Icons.Filled.ArrowBack, null)
                                    }
                                }
                            } else {
                                null
                            }
                        )
                    },
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
                        val onFabClick = layoutData.onFabSelected
                        if (onFabClick != null) {
                            FloatingActionButton(
                                shape = CircleShape,
                                onClick = onFabClick
                            ) {
                                Icon(Icons.Rounded.Add, null)
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
                            layoutData = SlotLayoutData(
                                stringResource(id = R.string.select_experiment),
                                onFabSelected = {
                                    navController.navigate(IMUScreen.CreateExperiment.route)
                                }
                            )
                            ExperimentListScreenWithNav(navController = navController)
                        }
                        composable(BottomTab.Label.route) {
                            layoutData = SlotLayoutData(
                                stringResource(id = R.string.label_list),
                                onFabSelected = {
                                    navController.navigate(IMUScreen.CreateLabel.route)
                                }
                            )
                            LabelListScreenWithNav(navController = navController)
                        }
                        composable(BottomTab.Model.route) {
                            layoutData = SlotLayoutData(stringResource(id = R.string.ai_model))
                            IntelligenceModelScreenWithNav(navController = navController)
                        }
                        composable(IMUScreen.CustomExperimentSetup.route) {
                            layoutData = SlotLayoutData(
                                stringResource(id = R.string.custom_experiment),
                                isBackButtonEnabled = true
                            )
                            CustomExperimentSetupScreenWithNav(navController = navController)
                        }
                        composable(IMUScreen.CreateLabel.route) {
                            layoutData = SlotLayoutData(
                                text = stringResource(id = R.string.create_label),
                                isBackButtonEnabled = true
                            )
                            CreateLabelScreenMain(navController = navController)
                        }
                        composable(IMUScreen.CreateExperiment.route) {
                            FullScreenText(text = "Create an experiment")
                        }
                        composable(
                            route = IMUScreen.RunExperiment.route,
                            arguments = listOf(navArgument("id") { type = NavType.LongType })
                        ) {
                            ExperimentScreenWithNav(
                                navController = navController,
                                experimentId = requireNotNull(it.arguments?.getLong("id")),
                                onUpdateMainLayout = { layoutData = it }
                            )
                        }
                    }
                }
            }
        }
    }
}

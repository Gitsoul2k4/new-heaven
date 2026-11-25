package com.example.ui.navigation

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AutoStories
import androidx.compose.material.icons.outlined.Explore
import androidx.compose.material.icons.outlined.PersonOutline
import androidx.compose.material.icons.rounded.AutoStories
import androidx.compose.material.icons.rounded.Explore
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.R
import com.example.ui.GameViewModel
import com.example.ui.screens.CityHubScreen
import com.example.ui.screens.DayOutcomeScreen
import com.example.ui.screens.LifeProfileScreen
import com.example.ui.screens.MemoriesScreen
import com.example.ui.screens.SceneInteractionScreen

const val ROUTE_CITY_HUB = "city_hub"
const val ROUTE_MEMORIES = "memories"
const val ROUTE_LIFE_PROFILE = "life_profile"
const val ROUTE_SCENE = "scene_interaction"
const val ROUTE_DAY_SUMMARY = "day_summary"

private data class TopLevelTab(
    val route: String,
    val labelRes: Int,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val testTag: String
)

private val topLevelTabs = listOf(
    TopLevelTab(
        route = ROUTE_CITY_HUB,
        labelRes = R.string.nav_city,
        selectedIcon = Icons.Rounded.Explore,
        unselectedIcon = Icons.Outlined.Explore,
        testTag = "nav_city"
    ),
    TopLevelTab(
        route = ROUTE_MEMORIES,
        labelRes = R.string.nav_memories,
        selectedIcon = Icons.Rounded.AutoStories,
        unselectedIcon = Icons.Outlined.AutoStories,
        testTag = "nav_memories"
    ),
    TopLevelTab(
        route = ROUTE_LIFE_PROFILE,
        labelRes = R.string.nav_life,
        selectedIcon = Icons.Rounded.Person,
        unselectedIcon = Icons.Outlined.PersonOutline,
        testTag = "nav_life"
    )
)

@Composable
fun AppNavigation(
    viewModel: GameViewModel = viewModel()
) {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    val world by viewModel.worldState.collectAsStateWithLifecycle()
    val npcs by viewModel.npcRelationships.collectAsStateWithLifecycle()
    val languageMemories by viewModel.languageMemories.collectAsStateWithLifecycle()
    val milestones by viewModel.milestones.collectAsStateWithLifecycle()
    val sceneUiState by viewModel.sceneUiState.collectAsStateWithLifecycle()
    val conversation by viewModel.sceneConversation.collectAsStateWithLifecycle()

    val lifeCapacities = viewModel.lifeCapacitiesFrom(world)
    val lifeStage = lifeCapacities.toStage()
    val pendingScene = viewModel.pendingStorySceneForCurrentWorld(world)

    val showChrome = currentRoute in setOf(ROUTE_CITY_HUB, ROUTE_MEMORIES, ROUTE_LIFE_PROFILE)

    fun navigateToTab(route: String) {
        navController.navigate(route) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val useRail = maxWidth >= 600.dp

        Scaffold(
            contentWindowInsets = if (showChrome) WindowInsets.safeDrawing else WindowInsets(0, 0, 0, 0),
            containerColor = MaterialTheme.colorScheme.background,
            bottomBar = {
                if (showChrome && !useRail) {
                    NavigationBar(
                        windowInsets = WindowInsets.navigationBars,
                        containerColor = MaterialTheme.colorScheme.surface
                    ) {
                        topLevelTabs.forEach { tab ->
                            val selected = backStackEntry?.destination?.hierarchy?.any { it.route == tab.route } == true
                            val label = stringResource(tab.labelRes)
                            NavigationBarItem(
                                selected = selected,
                                onClick = { navigateToTab(tab.route) },
                                icon = {
                                    Icon(
                                        imageVector = if (selected) tab.selectedIcon else tab.unselectedIcon,
                                        contentDescription = label
                                    )
                                },
                                label = { Text(label) },
                                modifier = Modifier.testTag(tab.testTag)
                            )
                        }
                    }
                }
            }
        ) { innerPadding ->
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                if (showChrome && useRail) {
                    NavigationRail(
                        containerColor = MaterialTheme.colorScheme.surface
                    ) {
                        topLevelTabs.forEach { tab ->
                            val selected = backStackEntry?.destination?.hierarchy?.any { it.route == tab.route } == true
                            val label = stringResource(tab.labelRes)
                            NavigationRailItem(
                                selected = selected,
                                onClick = { navigateToTab(tab.route) },
                                icon = {
                                    Icon(
                                        imageVector = if (selected) tab.selectedIcon else tab.unselectedIcon,
                                        contentDescription = label
                                    )
                                },
                                label = { Text(label) },
                                modifier = Modifier.testTag(tab.testTag)
                            )
                        }
                    }
                }

                NavHost(
                    navController = navController,
                    startDestination = ROUTE_CITY_HUB,
                    modifier = Modifier.weight(1f)
                ) {
                    composable(ROUTE_CITY_HUB) {
                        CityHubScreen(
                            world = world,
                            lifeStage = lifeStage,
                            pendingScene = pendingScene,
                            npcs = npcs,
                            onOpenScene = { scene ->
                                viewModel.openScene(scene)
                                navController.navigate(ROUTE_SCENE)
                            },
                            onOpenLocation = { locationId ->
                                viewModel.openLocationOrStory(locationId)
                                navController.navigate(ROUTE_SCENE)
                            },
                            onOpenDaySummary = {
                                navController.navigate(ROUTE_DAY_SUMMARY)
                            }
                        )
                    }

                    composable(ROUTE_MEMORIES) {
                        MemoriesScreen(
                            npcs = npcs,
                            languageMemories = languageMemories,
                            milestones = milestones
                        )
                    }

                    composable(ROUTE_LIFE_PROFILE) {
                        LifeProfileScreen(
                            world = world,
                            capacities = lifeCapacities,
                            isPlaceholderAiMode = viewModel.isPlaceholderAiMode,
                            onResetWorld = {
                                viewModel.resetWorld {
                                    navigateToTab(ROUTE_CITY_HUB)
                                }
                            }
                        )
                    }

                    composable(ROUTE_SCENE) {
                        val npcRel = npcs.firstOrNull { it.npcId == sceneUiState.scene?.npcId }
                        SceneInteractionScreen(
                            uiState = sceneUiState,
                            conversation = conversation,
                            npcRelationship = npcRel,
                            isPlaceholderAiMode = viewModel.isPlaceholderAiMode,
                            onBack = { navController.popBackStack() },
                            onToggleObserveCues = viewModel::toggleObserveCues,
                            onAdvanceMeaningSupport = viewModel::advanceMeaningSupport,
                            onSelectChoice = viewModel::selectChoice,
                            onContinueBeat = {
                                viewModel.continueToNextBeatOrFinish {
                                    navController.navigate(ROUTE_DAY_SUMMARY) {
                                        popUpTo(ROUTE_CITY_HUB)
                                    }
                                }
                            },
                            onSendFreeFormEnglish = viewModel::sendFreeFormEnglish
                        )
                    }

                    composable(ROUTE_DAY_SUMMARY) {
                        val todayMilestones = milestones.filter { it.day == world.currentDay }.reversed()
                        val todayMemories = languageMemories.filter { it.discoveredDay == world.currentDay }
                        DayOutcomeScreen(
                            world = world,
                            dayMilestones = todayMilestones,
                            dayMemories = todayMemories,
                            onNextDay = {
                                viewModel.advanceToNextDay {
                                    navController.popBackStack(ROUTE_CITY_HUB, inclusive = false)
                                }
                            },
                            onBackToCity = {
                                navController.popBackStack(ROUTE_CITY_HUB, inclusive = false)
                            }
                        )
                    }
                }
            }
        }
    }
}

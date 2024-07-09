package eu.merklaafe.chessclockdigital.navigation

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import eu.merklaafe.chessclockdigital.model.Player
import eu.merklaafe.chessclockdigital.model.ThemeConfig
import eu.merklaafe.chessclockdigital.presentation.screens.home.HomeScreen
import eu.merklaafe.chessclockdigital.presentation.screens.home.HomeViewModel
import eu.merklaafe.chessclockdigital.presentation.screens.settings.SettingsScreen
import eu.merklaafe.chessclockdigital.presentation.screens.settings.SettingsViewModel
import eu.merklaafe.chessclockdigital.presentation.screens.timeconfiguration.TimeConfigurationEvent
import eu.merklaafe.chessclockdigital.presentation.screens.timeconfiguration.TimeConfigurationScreen
import eu.merklaafe.chessclockdigital.presentation.screens.timeconfiguration.TimeConfigurationViewModel
import eu.merklaafe.chessclockdigital.presentation.screens.timeselection.TimeSelectionScreen
import eu.merklaafe.chessclockdigital.presentation.screens.timeselection.TimeSelectionViewModel

@Composable
fun NavigationGraph(
    startDestination: String,
    navHostController: NavHostController,
    systemBarVisible: (Boolean) -> Unit,
    keepScreenOn: (Boolean) -> Unit,
    themeConfigUpdate: (ThemeConfig) -> Unit
) {
    NavHost(
        navController = navHostController,
        startDestination = startDestination
    ) {
        homeRoute(
            navigateToTimeSelectionScreen = {
                navHostController.navigate(Screen.TimeSelection.route)
            },
            navigateToSettingsScreen = {
                navHostController.navigate(Screen.Settings.route)
            },
            systemBarVisible = systemBarVisible,
            keepScreenOn = keepScreenOn,
            themeConfigUpdate = themeConfigUpdate
        )

        timeSelectionRoute(
            navigateToHomeScreen = {
                val success = navHostController.popBackStack()
                if (!success) {
                    navHostController.navigate(Screen.Home.route)
                }
            },
            navigateToTimeConfigurationScreen = {
                navHostController.navigate(Screen.TimeConfiguration.route)
            },
            systemBarVisible = systemBarVisible,
            keepScreenOn = keepScreenOn
        )

        timeConfigurationRoute(
            navigateToTimeSelectionScreen = {
                navHostController.popBackStack()
            },
            systemBarVisible = systemBarVisible,
            keepScreenOn = keepScreenOn
        )

        settingsRoute(
            navigateToHomeScreen = {
                val success = navHostController.popBackStack()
                if (!success) {
                    navHostController.navigate(Screen.Home.route)
                }
            },
            systemBarVisible = systemBarVisible,
            keepScreenOn = keepScreenOn,
            themeConfigUpdate = themeConfigUpdate
        )
    }
}

// Extension functions NavGraphBuilder (Routes)
fun NavGraphBuilder.homeRoute(
    navigateToTimeSelectionScreen: () -> Unit,
    navigateToSettingsScreen: () -> Unit,
    systemBarVisible: (Boolean) -> Unit,
    keepScreenOn: (Boolean) -> Unit,
    themeConfigUpdate: (ThemeConfig) -> Unit
) {
    composable(route = Screen.Home.route) {
        //var initScreen by rememberSaveable { mutableStateOf(true) }
        val viewModel: HomeViewModel = hiltViewModel()
        val homeState = viewModel.homeState.value

        systemBarVisible(!viewModel.preferenceFullScreen.value)
        keepScreenOn(true)
        themeConfigUpdate(viewModel.preferenceThemeConfig.value)

        HomeScreen(
            timer1State = homeState.timer1,
            timer2State = homeState.timer2,
            movesPlayer1 = homeState.moves1,
            movesPlayer2 = homeState.moves2,
            gameState = homeState.gameState,
            playersTurn = homeState.playersTurn,
            preferenceSound = viewModel.preferenceSound.value,
            preferenceShowOpponentTimePortrait = viewModel.preferenceShowOpponentTimePortrait.value,
            preferenceShowOpponentTimeLandscape = viewModel.preferenceShowOpponentTimeLandscape.value,
            preferenceShowGameInfoPortrait = viewModel.preferenceShowGameInfoPortrait.value,
            preferenceShowGameInfoLandscape = viewModel.preferenceShowGameInfoLandscape.value,
            onPlayer1Click = { viewModel.inputPlayer1() },
            onPlayer2Click = { viewModel.inputPlayer2() },
            onPlayPauseClick = { viewModel.inputPlayPause() },
            onRefreshClick = { viewModel.reset() },
            onEditClick = navigateToTimeSelectionScreen,
            onSettingsClick = navigateToSettingsScreen
        )
    }
}

fun NavGraphBuilder.timeSelectionRoute(
    navigateToHomeScreen: () -> Unit,
    navigateToTimeConfigurationScreen: () -> Unit,
    systemBarVisible: (Boolean) -> Unit,
    keepScreenOn: (Boolean) -> Unit
) {
    composable(route = Screen.TimeSelection.route) {
        //systemBarVisible(true)
        keepScreenOn(false)
        val viewModel: TimeSelectionViewModel = hiltViewModel()
        val timeSelectionState = viewModel.timeSelectionState.value
        val selectedGameIdState = viewModel.selectedGameId.value
        Log.d("NavigationGraph", "Collected game id: ${viewModel.selectedGameId.value}")
        TimeSelectionScreen(
            presetGames = timeSelectionState.presetGames,
            onBackClick = navigateToHomeScreen,
            selectedGameId = selectedGameIdState,
            showDeleteOption = timeSelectionState.showDeleteOption,
            onSetSelectionClick = {
                viewModel.setSelectedGame(it)
            },
            onAddTimeControlClick = {
                viewModel.updateDeleteOption(false)
                navigateToTimeConfigurationScreen()
            },
            onDeleteOptionChange = {
                viewModel.updateDeleteOption(it)
            },
            onDeleteItemClick = {
                viewModel.deletePresetGame(it)
            }
        )
        BackHandler (
            onBack = navigateToHomeScreen
        )
    }
}

fun NavGraphBuilder.timeConfigurationRoute(
    navigateToTimeSelectionScreen: () -> Unit,
    systemBarVisible: (Boolean) -> Unit,
    keepScreenOn: (Boolean) -> Unit
) {
    composable(route = Screen.TimeConfiguration.route) {
        //systemBarVisible(true)
        keepScreenOn(false)
        val viewModel: TimeConfigurationViewModel = hiltViewModel()
        val timeConfigurationSate = viewModel.timeConfigurationState.value

        TimeConfigurationScreen(
            snackbarMessage = timeConfigurationSate.snackbarMessage,

            standardTimePlayer1HourState = timeConfigurationSate.standardTimePlayer1HourState,
            standardTimePlayer1MinuteState = timeConfigurationSate.standardTimePlayer1MinuteState,
            standardTimePlayer1SecondState = timeConfigurationSate.standardTimePlayer1SecondState,
            standardTimePlayer2HourState = timeConfigurationSate.standardTimePlayer2HourState,
            standardTimePlayer2MinuteState = timeConfigurationSate.standardTimePlayer2MinuteState,
            standardTimePlayer2SecondState = timeConfigurationSate.standardTimePlayer2SecondState,
            standardIncrementPlayer1HourState = timeConfigurationSate.standardIncrementPlayer1HourState,
            standardIncrementPlayer1MinuteState = timeConfigurationSate.standardIncrementPlayer1MinuteState,
            standardIncrementPlayer1SecondState = timeConfigurationSate.standardIncrementPlayer1SecondState,
            standardIncrementPlayer2HourState = timeConfigurationSate.standardIncrementPlayer2HourState,
            standardIncrementPlayer2MinuteState = timeConfigurationSate.standardIncrementPlayer2MinuteState,
            standardIncrementPlayer2SecondState = timeConfigurationSate.standardIncrementPlayer2SecondState,
            standardIncrementTypePlayer1 = timeConfigurationSate.standardIncrementTypePlayer1,
            standardIncrementTypePlayer2 = timeConfigurationSate.standardIncrementTypePlayer2,

            onStandardIncrementTypePlayer1Updated = {
                viewModel.onEvent(TimeConfigurationEvent.StandardIncrementTypeUpdate(
                    player = Player.Player1,
                    incrementType = it
                ))
            },
            onStandardIncrementTypePlayer2Updated = {
                viewModel.onEvent(TimeConfigurationEvent.StandardIncrementTypeUpdate(
                    player = Player.Player2,
                    incrementType = it
                ))
            },

            firstAddOnTimePlayer1HourState = timeConfigurationSate.firstAddOnTimePlayer1HourState,
            firstAddOnTimePlayer1MinuteState = timeConfigurationSate.firstAddOnTimePlayer1MinuteState,
            firstAddOnTimePlayer1SecondState = timeConfigurationSate.firstAddOnTimePlayer1SecondState,
            firstAddOnTimePlayer2HourState = timeConfigurationSate.firstAddOnTimePlayer2HourState,
            firstAddOnTimePlayer2MinuteState = timeConfigurationSate.firstAddOnTimePlayer2MinuteState,
            firstAddOnTimePlayer2SecondState = timeConfigurationSate.firstAddOnTimePlayer2SecondState,
            firstAddOnIncrementPlayer1HourState = timeConfigurationSate.firstAddOnIncrementPlayer1HourState,
            firstAddOnIncrementPlayer1MinuteState = timeConfigurationSate.firstAddOnIncrementPlayer1MinuteState,
            firstAddOnIncrementPlayer1SecondState = timeConfigurationSate.firstAddOnIncrementPlayer1SecondState,
            firstAddOnIncrementPlayer2HourState = timeConfigurationSate.firstAddOnIncrementPlayer2HourState,
            firstAddOnIncrementPlayer2MinuteState = timeConfigurationSate.firstAddOnIncrementPlayer2MinuteState,
            firstAddOnIncrementPlayer2SecondState = timeConfigurationSate.firstAddOnIncrementPlayer2SecondState,
            firstAddOnMovesPlayer1 = timeConfigurationSate.firstAddOnMovePlayer1,
            firstAddOnMovesPlayer2 = timeConfigurationSate.firstAddOnMovePlayer2,
            firstAddOnIncrementTypePlayer1 = timeConfigurationSate.firstAddOnIncrementTypePlayer1,
            firstAddOnIncrementTypePlayer2 = timeConfigurationSate.firstAddOnIncrementTypePlayer2,

            onFirstAddOnMovesPlayer1Updated = {
                viewModel.onEvent(TimeConfigurationEvent.FirstAddOnMoveUpdate(
                    player = Player.Player1,
                    move = it
                ))
            },
            onFirstAddOnMovesPlayer2Updated = {
                viewModel.onEvent(TimeConfigurationEvent.FirstAddOnMoveUpdate(
                    player = Player.Player2,
                    move = it
                ))
            },
            onFirstAddOnIncrementTypePlayer1Updated = {
                viewModel.onEvent(TimeConfigurationEvent.FirstAddOnIncrementTypeUpdate(
                    player = Player.Player1,
                    incrementType = it
                ))
            },
            onFirstAddOnIncrementTypePlayer2Updated = {
                viewModel.onEvent(TimeConfigurationEvent.FirstAddOnIncrementTypeUpdate(
                    player = Player.Player2,
                    incrementType = it
                ))
            },

            secondAddOnTimePlayer1HourState = timeConfigurationSate.secondAddOnTimePlayer1HourState,
            secondAddOnTimePlayer1MinuteState = timeConfigurationSate.secondAddOnTimePlayer1MinuteState,
            secondAddOnTimePlayer1SecondState = timeConfigurationSate.secondAddOnTimePlayer1SecondState,
            secondAddOnTimePlayer2HourState = timeConfigurationSate.secondAddOnTimePlayer2HourState,
            secondAddOnTimePlayer2MinuteState = timeConfigurationSate.secondAddOnTimePlayer2MinuteState,
            secondAddOnTimePlayer2SecondState = timeConfigurationSate.secondAddOnTimePlayer2SecondState,
            secondAddOnIncrementPlayer1HourState = timeConfigurationSate.secondAddOnIncrementPlayer1HourState,
            secondAddOnIncrementPlayer1MinuteState = timeConfigurationSate.secondAddOnIncrementPlayer1MinuteState,
            secondAddOnIncrementPlayer1SecondState = timeConfigurationSate.secondAddOnIncrementPlayer1SecondState,
            secondAddOnIncrementPlayer2HourState = timeConfigurationSate.secondAddOnIncrementPlayer2HourState,
            secondAddOnIncrementPlayer2MinuteState = timeConfigurationSate.secondAddOnIncrementPlayer2MinuteState,
            secondAddOnIncrementPlayer2SecondState = timeConfigurationSate.secondAddOnIncrementPlayer2SecondState,
            secondAddOnMovesPlayer1 = timeConfigurationSate.secondAddOnMovePlayer1,
            secondAddOnMovesPlayer2 = timeConfigurationSate.secondAddOnMovePlayer2,
            secondAddOnIncrementTypePlayer1 = timeConfigurationSate.secondAddOnIncrementTypePlayer1,
            secondAddOnIncrementTypePlayer2 = timeConfigurationSate.secondAddOnIncrementTypePlayer2,

            onSecondAddOnMovesPlayer1Updated = {
                viewModel.onEvent(TimeConfigurationEvent.SecondAddOnMoveUpdate(
                    player = Player.Player1,
                    move = it
                ))
            },
            onSecondAddOnMovesPlayer2Updated = {
                viewModel.onEvent(TimeConfigurationEvent.SecondAddOnMoveUpdate(
                    player = Player.Player2,
                    move = it
                ))
            },
            onSecondAddOnIncrementTypePlayer1Updated = {
                viewModel.onEvent(TimeConfigurationEvent.SecondAddOnIncrementTypeUpdate(
                    player = Player.Player1,
                    incrementType = it
                ))
            },
            onSecondAddOnIncrementTypePlayer2Updated = {
                viewModel.onEvent(TimeConfigurationEvent.SecondAddOnIncrementTypeUpdate(
                    player = Player.Player2,
                    incrementType = it
                ))
            },

            showPlayer2Content = timeConfigurationSate.differentPlayerSettings,
            onShowPlayer2ContentChange = {
                viewModel.onEvent(TimeConfigurationEvent.DifferentPlayerSettings(it))
            },

            showAddOn1Content = timeConfigurationSate.firstAddOnSetting,
            onShowAddOn1ContentChange = {
                viewModel.onEvent(TimeConfigurationEvent.FirstAddOnSetting(it))
            },

            showAddOn2Content = timeConfigurationSate.secondAddOnSetting,
            onShowAddOn2ContentChange = {
                viewModel.onEvent(TimeConfigurationEvent.SecondAddOnSetting(it))
            },

            onSaveClick = {
                viewModel.onEvent(TimeConfigurationEvent.Save(
                    onSuccess = {
                        navigateToTimeSelectionScreen()
                    }
                ))
            },
            onBackClick = navigateToTimeSelectionScreen,
            onSnackbarDismissed = {
                viewModel.onEvent(TimeConfigurationEvent.SnackbarDismissed)
            }
        )
    }
}

fun NavGraphBuilder.settingsRoute(
    navigateToHomeScreen: () -> Unit,
    systemBarVisible: (Boolean) -> Unit,
    keepScreenOn: (Boolean) -> Unit,
    themeConfigUpdate: (ThemeConfig) -> Unit
) {
    composable(route = Screen.Settings.route) {
        val viewModel: SettingsViewModel = hiltViewModel()
        systemBarVisible(!viewModel.preferenceFullScreen.value)
        keepScreenOn(false)

        LaunchedEffect(key1 = viewModel.preferenceFullScreen.value) {
            systemBarVisible(!viewModel.preferenceFullScreen.value)
        }

        SettingsScreen (
            onBackClick = navigateToHomeScreen,

            preferenceSound = viewModel.preferenceSound.value,
            preferenceFullScreen = viewModel.preferenceFullScreen.value,
            preferenceShowOpponentTimePortrait = viewModel.preferenceShowOpponentTimePortrait.value,
            preferenceShowOpponentTimeLandscape = viewModel.preferenceShowOpponentTimeLandscape.value,
            preferenceShowGameInfoPortrait = viewModel.preferenceShowGameInfoPortrait.value,
            preferenceShowGameInfoLandscape = viewModel.preferenceShowGameInfoLandscape.value,
            preferenceThemeConfig = viewModel.preferenceThemeConfig.value,

            onPreferenceFullScreenChange = {
                viewModel.setFullScreen(it)
            },
            onPreferenceShowOpponentTimePortraitChange = {
                viewModel.setShowOpponentTimePortrait(it)
            },
            onPreferenceShowOpponentTimeLandscapeChange = {
                viewModel.setShowOpponentTimeLandscape(it)
            },
            onPreferenceShowGameInfoPortraitChange = {
                viewModel.setShowGameInfoPortrait(it)
            },
            onPreferenceShowGameInfoLandscapeChange = {
                viewModel.setShowGameInfoLandscape(it)
            },
            onPreferenceSoundChange = {
                viewModel.setSound(it)
            },
            onPreferenceThemeConfigChange = {
                viewModel.setThemeConfig(it)
                themeConfigUpdate(it)
            }
        )
    }
}
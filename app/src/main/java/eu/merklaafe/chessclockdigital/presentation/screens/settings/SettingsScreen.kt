package eu.merklaafe.chessclockdigital.presentation.screens.settings

import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import eu.merklaafe.chessclockdigital.model.ThemeConfig
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@Composable
fun SettingsScreen(
    onBackClick: () -> Unit,

    preferenceSound: Boolean,
    preferenceFullScreen: Boolean,
    preferenceShowOpponentTimePortrait: Boolean,
    preferenceShowOpponentTimeLandscape: Boolean,
    preferenceShowGameInfoPortrait: Boolean,
    preferenceShowGameInfoLandscape: Boolean,
    preferenceThemeConfig: ThemeConfig,

    onPreferenceSoundChange: (Boolean) -> Unit,
    onPreferenceFullScreenChange: (Boolean) -> Unit,
    onPreferenceShowOpponentTimePortraitChange: (Boolean) -> Unit,
    onPreferenceShowOpponentTimeLandscapeChange: (Boolean) -> Unit,
    onPreferenceShowGameInfoPortraitChange: (Boolean) -> Unit,
    onPreferenceShowGameInfoLandscapeChange: (Boolean) -> Unit,
    onPreferenceThemeConfigChange: (ThemeConfig) -> Unit,
) {
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold (
        topBar = {
            SettingsTopBar(onBackClick = onBackClick)
        },
        content = {
            SettingsContent(
                paddingValues = it,

                preferenceSound = preferenceSound,
                preferenceFullScreen = preferenceFullScreen,
                preferenceShowOpponentTimePortrait = preferenceShowOpponentTimePortrait,
                preferenceShowOpponentTimeLandscape = preferenceShowOpponentTimeLandscape,
                preferenceShowGameInfoPortrait = preferenceShowGameInfoPortrait,
                preferenceShowGameInfoLandscape = preferenceShowGameInfoLandscape,
                preferenceThemeConfig = preferenceThemeConfig,

                onPreferenceSoundChange = onPreferenceSoundChange,
                onPreferenceFullScreenChange = onPreferenceFullScreenChange,
                onPreferenceShowOpponentTimePortraitChange = onPreferenceShowOpponentTimePortraitChange,
                onPreferenceShowOpponentTimeLandscapeChange = onPreferenceShowOpponentTimeLandscapeChange,
                onPreferenceShowGameInfoPortraitChange = onPreferenceShowGameInfoPortraitChange,
                onPreferenceShowGameInfoLandscapeChange = onPreferenceShowGameInfoLandscapeChange,
                onPreferenceThemeConfigChange = onPreferenceThemeConfigChange,

                feedbackSystemSoundMuted = {
                    scope.launch {
                        if (snackbarHostState.currentSnackbarData == null) {
                            snackbarHostState.showSnackbar(
                                message = "Sound effects muted by media volume: \nTurn up media volume to experience sound effects of this app."
                            )
                        }
                    }
                }
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    )
}
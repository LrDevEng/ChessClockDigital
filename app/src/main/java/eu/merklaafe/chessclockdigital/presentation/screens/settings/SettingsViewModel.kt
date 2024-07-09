package eu.merklaafe.chessclockdigital.presentation.screens.settings

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import eu.merklaafe.chessclockdigital.model.ThemeConfig
import eu.merklaafe.chessclockdigital.presentation.repository.PreferencesRepository
import eu.merklaafe.chessclockdigital.util.Constants
import eu.merklaafe.chessclockdigital.util.Constants.KEY_FULL_SCREEN
import eu.merklaafe.chessclockdigital.util.Constants.KEY_SHOW_GAME_INFO_LANDSCAPE
import eu.merklaafe.chessclockdigital.util.Constants.KEY_SHOW_GAME_INFO_PORTRAIT
import eu.merklaafe.chessclockdigital.util.Constants.KEY_SHOW_OPPONENT_TIME_LANDSCAPE
import eu.merklaafe.chessclockdigital.util.Constants.KEY_SHOW_OPPONENT_TIME_PORTRAIT
import eu.merklaafe.chessclockdigital.util.Constants.KEY_SOUND
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val preferencesRepository: PreferencesRepository
): ViewModel() {
    private val _preferenceSound = mutableStateOf(true)
    val preferenceSound: State<Boolean> = _preferenceSound

    private val _preferenceFullScreen = mutableStateOf(true)
    val preferenceFullScreen: State<Boolean> = _preferenceFullScreen

    private val _preferenceShowOpponentTimePortrait = mutableStateOf(true)
    val preferenceShowOpponentTimePortrait: State<Boolean> = _preferenceShowOpponentTimePortrait

    private val _preferenceShowOpponentTimeLandscape = mutableStateOf(false)
    val preferenceShowOpponentTimeLandscape: State<Boolean> = _preferenceShowOpponentTimeLandscape

    private val _preferenceShowGameInfoPortrait = mutableStateOf(true)
    val preferenceShowGameInfoPortrait: State<Boolean> = _preferenceShowGameInfoPortrait

    private val _preferenceShowGameInfoLandscape = mutableStateOf(true)
    val preferenceShowGameInfoLandscape: State<Boolean> = _preferenceShowGameInfoLandscape

    private val _preferenceThemeConfig = mutableStateOf(ThemeConfig.System)
    val preferenceThemeConfig: State<ThemeConfig> = _preferenceThemeConfig

    init {
        viewModelScope.launch {
            preferencesRepository.getPreferences()
                .collect() {
                    _preferenceSound.value = it[KEY_SOUND]?: true
                    _preferenceFullScreen.value = it[KEY_FULL_SCREEN]?: true
                    _preferenceShowOpponentTimePortrait.value = it[KEY_SHOW_OPPONENT_TIME_PORTRAIT]?: true
                    _preferenceShowOpponentTimeLandscape.value = it[KEY_SHOW_OPPONENT_TIME_LANDSCAPE]?: false
                    _preferenceShowGameInfoPortrait.value = it[KEY_SHOW_GAME_INFO_PORTRAIT]?: true
                    _preferenceShowGameInfoLandscape.value = it[KEY_SHOW_GAME_INFO_LANDSCAPE]?: true
                    _preferenceThemeConfig.value = ThemeConfig.entries[(it[Constants.KEY_THEME_CONFIG]?: ThemeConfig.System.ordinal)]
                }
        }
    }

    fun setSound(on: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            preferencesRepository.setSound(on)
        }
    }

    fun setFullScreen(on: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            preferencesRepository.setFullScreen(on)
        }
    }

    fun setShowOpponentTimePortrait(on: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            preferencesRepository.setShowOpponentTimePortrait(on)
        }
    }

    fun setShowOpponentTimeLandscape(on: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            preferencesRepository.setShowOpponentTimeLandscape(on)
        }
    }

    fun setShowGameInfoPortrait(on: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            preferencesRepository.setShowGameInfoPortrait(on)
        }
    }

    fun setShowGameInfoLandscape(on: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            preferencesRepository.setShowGameInfoLandscape(on)
        }
    }

    fun setThemeConfig(themeConfig: ThemeConfig) {
        viewModelScope.launch(Dispatchers.IO) {
            preferencesRepository.setThemeConfig(themeConfig.ordinal)
        }
    }
}
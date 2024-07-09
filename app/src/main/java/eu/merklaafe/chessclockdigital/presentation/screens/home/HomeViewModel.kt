package eu.merklaafe.chessclockdigital.presentation.screens.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import eu.merklaafe.chessclockdigital.model.ThemeConfig
import eu.merklaafe.chessclockdigital.presentation.repository.ChessClockRepository
import eu.merklaafe.chessclockdigital.presentation.repository.PreferencesRepository
import eu.merklaafe.chessclockdigital.util.Constants
import eu.merklaafe.chessclockdigital.util.Constants.KEY_SOUND
import eu.merklaafe.chessclockdigital.util.parseMillisToDigits
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val chessClockRepository: ChessClockRepository,
    private val preferencesRepository: PreferencesRepository
): ViewModel() {
    private val _homeState = mutableStateOf(HomeState())
    val homeState: State<HomeState> = _homeState

    private val _preferenceSound = mutableStateOf(false)
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
        viewModelScope.launch(Dispatchers.IO) {
            chessClockRepository.reset()
        }

        viewModelScope.launch {
            chessClockRepository.receiveGameUpdates()
                .flowOn(Dispatchers.IO)
                .collectLatest {currentGameState ->
                _homeState.value = _homeState.value.copy(
                    timer1 = parseMillisToDigits(currentGameState.timer1),
                    timer2 = parseMillisToDigits(currentGameState.timer2),
                    gameState = currentGameState.gameState,
                    moves1 = currentGameState.movesPlayer1,
                    moves2 = currentGameState.movesPlayer2,
                    playersTurn = currentGameState.playersTurn
                )
            }
        }

        viewModelScope.launch {
            preferencesRepository.getPreferences()
                .collect() {
                    _preferenceSound.value = it[KEY_SOUND]?: true
                    _preferenceFullScreen.value = it[Constants.KEY_FULL_SCREEN]?: true
                    _preferenceShowOpponentTimePortrait.value = it[Constants.KEY_SHOW_OPPONENT_TIME_PORTRAIT]?: true
                    _preferenceShowOpponentTimeLandscape.value = it[Constants.KEY_SHOW_OPPONENT_TIME_LANDSCAPE]?: false
                    _preferenceShowGameInfoPortrait.value = it[Constants.KEY_SHOW_GAME_INFO_PORTRAIT]?: true
                    _preferenceShowGameInfoLandscape.value = it[Constants.KEY_SHOW_GAME_INFO_LANDSCAPE]?: true
                    _preferenceThemeConfig.value = ThemeConfig.entries[(it[Constants.KEY_THEME_CONFIG]?: ThemeConfig.System.ordinal)]
                }
        }
    }

    fun inputPlayer1() {
        viewModelScope.launch {
            chessClockRepository.inputPlayer1()
        }
    }

    fun inputPlayer2() {
        viewModelScope.launch {
            chessClockRepository.inputPlayer2()
        }
    }

    fun inputPlayPause() {
        viewModelScope.launch {
            chessClockRepository.inputPlayPause()
        }
    }

    fun reset() {
        viewModelScope.launch {
            chessClockRepository.reset()
        }
    }
}
package eu.merklaafe.chessclockdigital.presentation.screens.home

import androidx.compose.runtime.Stable
import eu.merklaafe.chessclockdigital.model.GameState
import eu.merklaafe.chessclockdigital.model.Player
import eu.merklaafe.chessclockdigital.model.Player.*

@Stable
data class HomeState (
    val timer1: String = "",
    val timer2: String = "",
    val gameState: GameState = GameState.Idle,
    val moves1: Int = 0,
    val moves2: Int = 0,
    val playersTurn: Player = Player1
)
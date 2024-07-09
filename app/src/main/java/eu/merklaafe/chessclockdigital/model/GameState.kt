package eu.merklaafe.chessclockdigital.model

sealed class GameState {
    data object Idle: GameState()
    data object Running: GameState()
    data object Paused: GameState()
    data object Finished: GameState()
}
package eu.merklaafe.chessclockdigital.data_source.cache

import eu.merklaafe.chessclockdigital.model.GamePeriod
import eu.merklaafe.chessclockdigital.model.GameState
import eu.merklaafe.chessclockdigital.model.IncrementType
import eu.merklaafe.chessclockdigital.model.Player

data class CurrentGameState (
    val gameState: GameState,
    val playersTurn: Player,

    val timer1: Long,
    val movesPlayer1: Int,
    val gamePeriodPlayer1: GamePeriod,
    val incrementPlayer1: Long,
    val incrementTypePlayer1: IncrementType,
    val startTimePlayer1: Long,

    val timer2: Long,
    val movesPlayer2: Int,
    val gamePeriodPlayer2: GamePeriod,
    val incrementPlayer2: Long,
    val incrementTypePlayer2: IncrementType,
    val startTimePlayer2: Long
)
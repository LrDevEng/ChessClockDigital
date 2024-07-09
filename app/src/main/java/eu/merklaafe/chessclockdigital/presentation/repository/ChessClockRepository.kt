package eu.merklaafe.chessclockdigital.presentation.repository

import eu.merklaafe.chessclockdigital.data_source.cache.CurrentGameState
import eu.merklaafe.chessclockdigital.data_source.database.entity.PresetGame
import kotlinx.coroutines.flow.Flow

interface ChessClockRepository {
    // Chess game logic interface
    suspend fun inputPlayer1()
    suspend fun inputPlayer2()
    suspend fun inputPlayPause()
    suspend fun reset()

    suspend fun receiveGameUpdates(): Flow<CurrentGameState>
}
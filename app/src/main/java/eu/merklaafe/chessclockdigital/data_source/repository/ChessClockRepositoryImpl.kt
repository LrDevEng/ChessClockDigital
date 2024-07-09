package eu.merklaafe.chessclockdigital.data_source.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import eu.merklaafe.chessclockdigital.data_source.cache.CurrentGame
import eu.merklaafe.chessclockdigital.data_source.cache.CurrentGameObserver
import eu.merklaafe.chessclockdigital.data_source.cache.CurrentGameState
import eu.merklaafe.chessclockdigital.data_source.database.PresetChessGameDao
import eu.merklaafe.chessclockdigital.presentation.repository.ChessClockRepository
import eu.merklaafe.chessclockdigital.util.Constants.KEY_SELECTED_CHESS_GAME_ID
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.take
import javax.inject.Inject

class ChessClockRepositoryImpl @Inject constructor(
    private val presetChessGameDao: PresetChessGameDao,
    private val currentGame: CurrentGame,
    private val preferences: DataStore<Preferences>
): ChessClockRepository {

    override suspend fun inputPlayer1() {
        currentGame.inputPlayer1()
    }

    override suspend fun inputPlayer2() {
        currentGame.inputPlayer2()
    }

    override suspend fun inputPlayPause() {
        currentGame.inputPlayPause()
    }

    override suspend fun reset() {
        // Get selected game id from data store preferences and reset current game accordingly
        val preferenceCurrentGame = preferences.data.map { prefs ->
            prefs[KEY_SELECTED_CHESS_GAME_ID]?: 0
        }

        preferenceCurrentGame.take(1).collect() { selectedGameId ->
            currentGame.reset(presetChessGameDao.getSelectedGame(selectedGameId))
        }
    }

    override suspend fun receiveGameUpdates(): Flow<CurrentGameState> = callbackFlow {
        val currentGameObserver = object: CurrentGameObserver {
            override fun onGameUpdated(currentGameState: CurrentGameState) {
                trySend(currentGameState)
            }
        }
        currentGame.subscribe(currentGameObserver)
        awaitClose {
            currentGame.unsubscribe(currentGameObserver)
        }
    }
}
package eu.merklaafe.chessclockdigital.data_source.repository

import android.util.Log
import eu.merklaafe.chessclockdigital.data_source.database.PresetChessGameDao
import eu.merklaafe.chessclockdigital.data_source.database.entity.PresetGame
import eu.merklaafe.chessclockdigital.presentation.repository.PresetGamesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class PresetGamesRepositoryImpl @Inject constructor(
    private val presetChessGameDao: PresetChessGameDao
) :PresetGamesRepository {

    override suspend fun getPresetGames(): Flow<List<PresetGame>> = presetChessGameDao.getAllChessGames()

    override suspend fun getPresetGame(id: Int): PresetGame = presetChessGameDao.getSelectedGame(id)

    override suspend fun insertPresetGame(presetGame: PresetGame): Int? {
        var id: Int? = null

        presetChessGameDao.getAllChessGamesOnce().forEach { game ->
            if (game.identicalTo(presetGame)) {
                id = game.id
                return@forEach
            }
        }

        if (id == null) {
            id = try {
                val rowId = presetChessGameDao.addChessGame(presetGame = presetGame)
                presetChessGameDao.getGame(rowId).id
            } catch (e: Exception) {
                Log.d("PresetGamesRepositoryImpl","Exception when inserting preset chess game: ${e.message}")
                null
            }
        }
        return id
    }

    override suspend fun deletePresetGame(id: Int): Boolean {
        return try {
            presetChessGameDao.deleteChessGame(id)
            true
        } catch (e: Exception) {
            Log.d("PresetGamesRepositoryImpl","Exception when deleting preset chess game with id $id: ${e.message}")
            false
        }
    }
}
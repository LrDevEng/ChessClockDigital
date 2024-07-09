package eu.merklaafe.chessclockdigital.presentation.repository

import eu.merklaafe.chessclockdigital.data_source.database.entity.PresetGame
import kotlinx.coroutines.flow.Flow

interface PresetGamesRepository {
    // Room database interface
    suspend fun getPresetGames(): Flow<List<PresetGame>>
    suspend fun getPresetGame(id: Int): PresetGame
    suspend fun insertPresetGame(presetGame: PresetGame): Int?
    suspend fun deletePresetGame(id: Int): Boolean
}
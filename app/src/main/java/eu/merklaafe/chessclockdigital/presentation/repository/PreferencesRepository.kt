package eu.merklaafe.chessclockdigital.presentation.repository

import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.flow.Flow

interface PreferencesRepository {
    // Data store preferences interface
    suspend fun setSelectedGame(id: Int)
    suspend fun getSelectedGame(): Flow<Int>

    suspend fun setThemeConfig(config: Int)
    suspend fun setSound(on: Boolean)
    suspend fun setFullScreen(on: Boolean)
    suspend fun setShowOpponentTimePortrait(on: Boolean)
    suspend fun setShowOpponentTimeLandscape(on: Boolean)
    suspend fun setShowGameInfoPortrait(on: Boolean)
    suspend fun setShowGameInfoLandscape(on: Boolean)

    suspend fun getPreferences(): Flow<Preferences>
}
package eu.merklaafe.chessclockdigital.data_source.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import eu.merklaafe.chessclockdigital.presentation.repository.PreferencesRepository
import eu.merklaafe.chessclockdigital.util.Constants.KEY_FULL_SCREEN
import eu.merklaafe.chessclockdigital.util.Constants.KEY_SELECTED_CHESS_GAME_ID
import eu.merklaafe.chessclockdigital.util.Constants.KEY_SHOW_GAME_INFO_LANDSCAPE
import eu.merklaafe.chessclockdigital.util.Constants.KEY_SHOW_GAME_INFO_PORTRAIT
import eu.merklaafe.chessclockdigital.util.Constants.KEY_SHOW_OPPONENT_TIME_LANDSCAPE
import eu.merklaafe.chessclockdigital.util.Constants.KEY_SHOW_OPPONENT_TIME_PORTRAIT
import eu.merklaafe.chessclockdigital.util.Constants.KEY_SOUND
import eu.merklaafe.chessclockdigital.util.Constants.KEY_THEME_CONFIG
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PreferencesRepositoryImpl @Inject constructor(
    private val preferences: DataStore<Preferences>
): PreferencesRepository {
    override suspend fun setSelectedGame(id: Int) {
        preferences.edit {
            it[KEY_SELECTED_CHESS_GAME_ID] = id
        }
    }

    override suspend fun getSelectedGame(): Flow<Int> {
        // Get selected game id from data store preferences and reset current game accordingly
        return preferences.data.map {prefs ->
            prefs[KEY_SELECTED_CHESS_GAME_ID]?: 0
        }
    }

    override suspend fun setThemeConfig(config: Int) {
        preferences.edit {
            it[KEY_THEME_CONFIG] = config
        }
    }

    override suspend fun setSound(on: Boolean) {
        preferences.edit {
            it[KEY_SOUND] = on
        }
    }

    override suspend fun setFullScreen(on: Boolean) {
        preferences.edit {
            it[KEY_FULL_SCREEN] = on
        }
    }

    override suspend fun setShowOpponentTimePortrait(on: Boolean) {
        preferences.edit {
            it[KEY_SHOW_OPPONENT_TIME_PORTRAIT] = on
        }
    }

    override suspend fun setShowOpponentTimeLandscape(on: Boolean) {
        preferences.edit {
            it[KEY_SHOW_OPPONENT_TIME_LANDSCAPE] = on
        }
    }

    override suspend fun setShowGameInfoPortrait(on: Boolean) {
        preferences.edit {
            it[KEY_SHOW_GAME_INFO_PORTRAIT] = on
        }
    }

    override suspend fun setShowGameInfoLandscape(on: Boolean) {
        preferences.edit {
            it[KEY_SHOW_GAME_INFO_LANDSCAPE] = on
        }
    }

    override suspend fun getPreferences(): Flow<Preferences> {
        return preferences.data
    }
}
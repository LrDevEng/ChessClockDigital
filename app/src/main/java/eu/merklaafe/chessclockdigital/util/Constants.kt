package eu.merklaafe.chessclockdigital.util

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import eu.merklaafe.chessclockdigital.R

object Constants {
    const val TENTH_OF_A_SECOND_IN_MILLIS = 100L

    // Local database
    const val CHESS_GAME_DATABASE = "chess_game_db"
    const val CHESS_GAME_TABLE = "chess_game_table"

    // Data store name & keys
    const val DATA_STORE_PREFERENCES_NAME = "preferences"
    private const val DATA_STORE_KEY_SELECTED_CHESS_GAME = "selected_chess_game"
    private const val DATA_STORE_KEY_SOUND = "sound"
    private const val DATA_STORE_KEY_FULL_SCREEN = "full_screen"
    private const val DATA_STORE_KEY_SHOW_OPPONENT_TIME_PORTRAIT = "show_opponent_time_portrait"
    private const val DATA_STORE_KEY_SHOW_OPPONENT_TIME_LANDSCAPE = "show_opponent_time_landscape"
    private const val DATA_STORE_KEY_SHOW_GAME_INFO_PORTRAIT = "show_game_info_portrait"
    private const val DATA_STORE_KEY_SHOW_GAME_INFO_LANDSCAPE = "show_game_info_landscape"
    private const val DATA_STORE_KEY_THEME_CONFIG = "theme_config"

    // Preferences
    val KEY_SELECTED_CHESS_GAME_ID = intPreferencesKey(DATA_STORE_KEY_SELECTED_CHESS_GAME)
    val KEY_SOUND = booleanPreferencesKey(DATA_STORE_KEY_SOUND)
    val KEY_FULL_SCREEN = booleanPreferencesKey(DATA_STORE_KEY_FULL_SCREEN)
    val KEY_SHOW_OPPONENT_TIME_PORTRAIT = booleanPreferencesKey(DATA_STORE_KEY_SHOW_OPPONENT_TIME_PORTRAIT)
    val KEY_SHOW_OPPONENT_TIME_LANDSCAPE = booleanPreferencesKey(DATA_STORE_KEY_SHOW_OPPONENT_TIME_LANDSCAPE)
    val KEY_SHOW_GAME_INFO_PORTRAIT = booleanPreferencesKey(DATA_STORE_KEY_SHOW_GAME_INFO_PORTRAIT)
    val KEY_SHOW_GAME_INFO_LANDSCAPE = booleanPreferencesKey(DATA_STORE_KEY_SHOW_GAME_INFO_LANDSCAPE)
    val KEY_THEME_CONFIG = intPreferencesKey(DATA_STORE_KEY_THEME_CONFIG)

    // Time
    const val MILLIS_PER_HOUR = 3600000
    const val MILLIS_PER_MINUTE = 60000
    const val MILLIS_PER_SECOND = 1000
    const val BULLET_GAME_LIMIT_IN_MILLIS = 180000
    const val BLITZ_GAME_LIMIT_IN_MILLIS = 900000
    const val RAPID_GAME_LIMIT_IN_MILLIS = 3600000

    // Custom fonts
    val digitFamily = FontFamily(
        Font(R.font.lcd_clock, FontWeight.Normal)
    )
}
package eu.merklaafe.chessclockdigital.presentation.screens.timeselection

import androidx.compose.runtime.Stable
import eu.merklaafe.chessclockdigital.data_source.database.entity.PresetGame

@Stable
data class TimeSelectionState (
    val presetGames: List<PresetGame> = emptyList(),
    val selectedGame: Int = 0,
    val showDeleteOption: Boolean = false
)
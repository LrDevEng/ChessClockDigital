package eu.merklaafe.chessclockdigital.presentation.screens.timeselection

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import eu.merklaafe.chessclockdigital.presentation.repository.ChessClockRepository
import eu.merklaafe.chessclockdigital.presentation.repository.PreferencesRepository
import eu.merklaafe.chessclockdigital.presentation.repository.PresetGamesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class TimeSelectionViewModel @Inject constructor(
    private val presetGamesRepository: PresetGamesRepository,
    private val preferencesRepository: PreferencesRepository,
    private val chessClockRepository: ChessClockRepository
): ViewModel() {

    private val _timeSelectionState = mutableStateOf(TimeSelectionState())
    val timeSelectionState: State<TimeSelectionState> = _timeSelectionState

    private val _selectedGameId = mutableIntStateOf(0)
    val selectedGameId: State<Int> = _selectedGameId

    init {
        viewModelScope.launch {
            presetGamesRepository.getPresetGames()
                .flowOn(Dispatchers.IO)
                .collect() {
                _timeSelectionState.value = _timeSelectionState.value.copy(
                    presetGames = it
                )
                if (it.size == 1) {
                    _timeSelectionState.value = _timeSelectionState.value.copy(
                        showDeleteOption = false
                    )
                }
            }
        }

        viewModelScope.launch {
            preferencesRepository.getSelectedGame()
                .collect {
                    _selectedGameId.intValue = it
                    Log.d("TimeSelectionViewModel", "Collected game id: $it")
            }
        }
    }

    fun setSelectedGame(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = viewModelScope.async(Dispatchers.IO) {
                preferencesRepository.setSelectedGame(id = id)
            }
            result.await()
            withContext(Dispatchers.IO) {
                chessClockRepository.reset()
            }
        }
    }

    fun updateDeleteOption(option: Boolean) {
        _timeSelectionState.value = _timeSelectionState.value.copy(
            showDeleteOption = option
        )
    }

    fun deletePresetGame(id: Int): Boolean {
        var deleted = false
        viewModelScope.launch {
            val result = viewModelScope.async(Dispatchers.IO) {
                deleted = presetGamesRepository.deletePresetGame(id)
            }
            result.await()
            if (!result.isCompleted)
                deleted = false
        }
        return deleted
    }
}
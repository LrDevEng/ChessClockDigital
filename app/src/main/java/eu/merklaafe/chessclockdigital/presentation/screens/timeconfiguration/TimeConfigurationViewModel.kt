package eu.merklaafe.chessclockdigital.presentation.screens.timeconfiguration

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import eu.merklaafe.chessclockdigital.data_source.database.entity.PresetGame
import eu.merklaafe.chessclockdigital.model.IncrementType
import eu.merklaafe.chessclockdigital.model.Player.*
import eu.merklaafe.chessclockdigital.model.TimeConfigurationStatus
import eu.merklaafe.chessclockdigital.presentation.repository.ChessClockRepository
import eu.merklaafe.chessclockdigital.presentation.repository.PreferencesRepository
import eu.merklaafe.chessclockdigital.presentation.repository.PresetGamesRepository
import eu.merklaafe.chessclockdigital.util.parseDigitsToMillis
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class TimeConfigurationViewModel @Inject constructor(
    private val presetGamesRepository: PresetGamesRepository,
    private val preferencesRepository: PreferencesRepository,
    private val chessClockRepository: ChessClockRepository
): ViewModel() {
    private val _timeConfigurationState = mutableStateOf(TimeConfigurationState())
    val timeConfigurationState: State<TimeConfigurationState> = _timeConfigurationState

    fun onEvent(event: TimeConfigurationEvent) {
        when(event) {
            is TimeConfigurationEvent.Save -> {
                viewModelScope.launch(Dispatchers.IO) {
                    val result = tryToSaveConfiguration()
                    Log.d("TimeConfigurationViewModel","Save result: $result")
                    when(result) {
                        TimeConfigurationStatus.SavedSuccessfully -> {
                            withContext(Dispatchers.Main) {
                                event.onSuccess()
                            }
                        }
                        TimeConfigurationStatus.SavingError -> {
                            withContext(Dispatchers.Main) {
                                _timeConfigurationState.value = _timeConfigurationState.value.copy(
                                    snackbarMessage = "Error: Could not save new time control."
                                )
                            }
                        }
                        TimeConfigurationStatus.StandardTimeIssue -> {
                            withContext(Dispatchers.Main) {
                                _timeConfigurationState.value = _timeConfigurationState.value.copy(
                                    snackbarMessage = "Initial time must be greater than 0."
                                )
                            }
                        }
                        TimeConfigurationStatus.StandardIncrementIssue -> {
                            withContext(Dispatchers.Main) {
                                _timeConfigurationState.value = _timeConfigurationState.value.copy(
                                    snackbarMessage = "Initial increment must be greater than 0."
                                )
                            }
                        }
                        TimeConfigurationStatus.FirstAddOnMoveIssue -> {
                            withContext(Dispatchers.Main) {
                                _timeConfigurationState.value = _timeConfigurationState.value.copy(
                                    snackbarMessage = "Add on (1) move must be between 1 and 999."
                                )
                            }
                        }
                        TimeConfigurationStatus.FirstAddOnTimeIssue -> {
                            withContext(Dispatchers.Main) {
                                _timeConfigurationState.value = _timeConfigurationState.value.copy(
                                    snackbarMessage = "Add on (1) time must be greater than 0."
                                )
                            }
                        }
                        TimeConfigurationStatus.FirstAddOnIncrementIssue -> {
                            withContext(Dispatchers.Main) {
                                _timeConfigurationState.value = _timeConfigurationState.value.copy(
                                    snackbarMessage = "Add on (1) increment must be greater than 0."
                                )
                            }
                        }
                        TimeConfigurationStatus.SecondAddOnMoveIssue -> {
                            withContext(Dispatchers.Main) {
                                _timeConfigurationState.value = _timeConfigurationState.value.copy(
                                    snackbarMessage = "Add on (2) move must be between 1 and 999."
                                )
                            }
                        }
                        TimeConfigurationStatus.SecondAddOnTimeIssue -> {
                            withContext(Dispatchers.Main) {
                                _timeConfigurationState.value = _timeConfigurationState.value.copy(
                                    snackbarMessage = "Add on (2) time must be greater than 0."
                                )
                            }
                        }
                        TimeConfigurationStatus.SecondAddOnIncrementIssue -> {
                            withContext(Dispatchers.Main) {
                                _timeConfigurationState.value = _timeConfigurationState.value.copy(
                                    snackbarMessage = "Add on (2) increment must be greater than 0."
                                )
                            }
                        }
                    }
                }

                //Log.d("TimeConfigurationViewModel","State: ${timeConfigurationState.value.standardTimePlayer2MinuteState.currentItem}")
            }
            is TimeConfigurationEvent.SnackbarDismissed -> {
                _timeConfigurationState.value = _timeConfigurationState.value.copy(
                    snackbarMessage = ""
                )
            }
            is TimeConfigurationEvent.DifferentPlayerSettings -> {
                _timeConfigurationState.value = _timeConfigurationState.value.copy(
                    differentPlayerSettings = event.value
                )
                samePlayerSettings()
            }
            is TimeConfigurationEvent.StandardIncrementTypeUpdate -> {
                when(event.player) {
                    is Player1 -> {
                        _timeConfigurationState.value = _timeConfigurationState.value.copy(
                            standardIncrementTypePlayer1 = event.incrementType
                        )
                    }
                    is Player2 -> {
                        _timeConfigurationState.value = _timeConfigurationState.value.copy(
                            standardIncrementTypePlayer2 = event.incrementType
                        )
                    }
                }
            }
            is TimeConfigurationEvent.FirstAddOnSetting -> {
                if (event.value) {
                    _timeConfigurationState.value = _timeConfigurationState.value.copy(
                        firstAddOnSetting = true
                    )
                } else {
                    _timeConfigurationState.value = _timeConfigurationState.value.copy(
                        firstAddOnSetting = false,
                        secondAddOnSetting = false
                    )
                }
            }
            is TimeConfigurationEvent.FirstAddOnMoveUpdate -> {
                var newValue = event.move
                while (newValue.length > 3) {
                    newValue = newValue.drop(1)
                }
                when(event.player) {
                    is Player1 -> {
                        _timeConfigurationState.value = _timeConfigurationState.value.copy(
                            firstAddOnMovePlayer1 = newValue
                        )
                    }
                    is Player2 -> {
                        _timeConfigurationState.value = _timeConfigurationState.value.copy(
                            firstAddOnMovePlayer2 = newValue
                        )
                    }
                }
            }
            is TimeConfigurationEvent.FirstAddOnIncrementTypeUpdate -> {
                when(event.player) {
                    is Player1 -> {
                        _timeConfigurationState.value = _timeConfigurationState.value.copy(
                            firstAddOnIncrementTypePlayer1 = event.incrementType
                        )
                    }
                    is Player2 -> {
                        _timeConfigurationState.value = _timeConfigurationState.value.copy(
                            firstAddOnIncrementTypePlayer2 = event.incrementType
                        )
                    }
                }
            }
            is TimeConfigurationEvent.SecondAddOnSetting -> {
                _timeConfigurationState.value = _timeConfigurationState.value.copy(
                    secondAddOnSetting = event.value
                )
            }
            is TimeConfigurationEvent.SecondAddOnMoveUpdate -> {
                var newValue = event.move
                while (newValue.length > 3) {
                    newValue = newValue.drop(1)
                }
                when(event.player) {
                    is Player1 -> {
                        _timeConfigurationState.value = _timeConfigurationState.value.copy(
                            secondAddOnMovePlayer1 = newValue
                        )
                    }
                    is Player2 -> {
                        _timeConfigurationState.value = _timeConfigurationState.value.copy(
                            secondAddOnMovePlayer2 = newValue
                        )
                    }
                }
            }
            is TimeConfigurationEvent.SecondAddOnIncrementTypeUpdate -> {
                when(event.player) {
                    is Player1 -> {
                        _timeConfigurationState.value = _timeConfigurationState.value.copy(
                            secondAddOnIncrementTypePlayer1 = event.incrementType
                        )
                    }
                    is Player2 -> {
                        _timeConfigurationState.value = _timeConfigurationState.value.copy(
                            secondAddOnIncrementTypePlayer2 = event.incrementType
                        )
                    }
                }
            }
        }
    }

    private fun samePlayerSettings() {
        _timeConfigurationState.value = _timeConfigurationState.value.copy(
            standardIncrementTypePlayer2 = _timeConfigurationState.value.standardIncrementTypePlayer1,
            firstAddOnIncrementTypePlayer2 = _timeConfigurationState.value.firstAddOnIncrementTypePlayer1,
            secondAddOnIncrementTypePlayer2 = _timeConfigurationState.value.secondAddOnIncrementTypePlayer1,
            firstAddOnMovePlayer2 = _timeConfigurationState.value.firstAddOnMovePlayer1,
            secondAddOnMovePlayer2 = _timeConfigurationState.value.secondAddOnMovePlayer1,
        )

        _timeConfigurationState.value.standardTimePlayer2HourState.update(_timeConfigurationState.value.standardTimePlayer1HourState.currentItem)
        _timeConfigurationState.value.standardTimePlayer2MinuteState.update(_timeConfigurationState.value.standardTimePlayer1MinuteState.currentItem)
        _timeConfigurationState.value.standardTimePlayer2SecondState.update(_timeConfigurationState.value.standardTimePlayer1SecondState.currentItem)

        _timeConfigurationState.value.standardIncrementPlayer2HourState.update(_timeConfigurationState.value.standardIncrementPlayer1HourState.currentItem)
        _timeConfigurationState.value.standardIncrementPlayer2MinuteState.update(_timeConfigurationState.value.standardIncrementPlayer1MinuteState.currentItem)
        _timeConfigurationState.value.standardIncrementPlayer2SecondState.update(_timeConfigurationState.value.standardIncrementPlayer1SecondState.currentItem)

        _timeConfigurationState.value.firstAddOnTimePlayer2HourState.update(_timeConfigurationState.value.firstAddOnTimePlayer1HourState.currentItem)
        _timeConfigurationState.value.firstAddOnTimePlayer2MinuteState.update(_timeConfigurationState.value.firstAddOnTimePlayer1MinuteState.currentItem)
        _timeConfigurationState.value.firstAddOnTimePlayer2SecondState.update(_timeConfigurationState.value.firstAddOnTimePlayer1SecondState.currentItem)

        _timeConfigurationState.value.firstAddOnIncrementPlayer2HourState.update(_timeConfigurationState.value.firstAddOnIncrementPlayer1HourState.currentItem)
        _timeConfigurationState.value.firstAddOnIncrementPlayer2MinuteState.update(_timeConfigurationState.value.firstAddOnIncrementPlayer1MinuteState.currentItem)
        _timeConfigurationState.value.firstAddOnIncrementPlayer2SecondState.update(_timeConfigurationState.value.firstAddOnIncrementPlayer1SecondState.currentItem)

        _timeConfigurationState.value.secondAddOnTimePlayer2HourState.update(_timeConfigurationState.value.secondAddOnTimePlayer1HourState.currentItem)
        _timeConfigurationState.value.secondAddOnTimePlayer2MinuteState.update(_timeConfigurationState.value.secondAddOnTimePlayer1MinuteState.currentItem)
        _timeConfigurationState.value.secondAddOnTimePlayer2SecondState.update(_timeConfigurationState.value.secondAddOnTimePlayer1SecondState.currentItem)

        _timeConfigurationState.value.secondAddOnIncrementPlayer2HourState.update(_timeConfigurationState.value.secondAddOnIncrementPlayer1HourState.currentItem)
        _timeConfigurationState.value.secondAddOnIncrementPlayer2MinuteState.update(_timeConfigurationState.value.secondAddOnIncrementPlayer1MinuteState.currentItem)
        _timeConfigurationState.value.secondAddOnIncrementPlayer2SecondState.update(_timeConfigurationState.value.secondAddOnIncrementPlayer1SecondState.currentItem)
    }

    private suspend fun tryToSaveConfiguration(): TimeConfigurationStatus {
        if (!_timeConfigurationState.value.differentPlayerSettings) {
            samePlayerSettings()
        }

        // Standard time player 1
        val standardTimePlayer1 = parseDigitsToMillis(
            digitsHour = _timeConfigurationState.value.standardTimePlayer1HourState.currentItem,
            digitsMinute = _timeConfigurationState.value.standardTimePlayer1MinuteState.currentItem,
            digitsSecond = _timeConfigurationState.value.standardTimePlayer1SecondState.currentItem
        )

        // Standard time player 2
        val standardTimePlayer2 = parseDigitsToMillis(
            digitsHour = _timeConfigurationState.value.standardTimePlayer2HourState.currentItem,
            digitsMinute = _timeConfigurationState.value.standardTimePlayer2MinuteState.currentItem,
            digitsSecond = _timeConfigurationState.value.standardTimePlayer2SecondState.currentItem
        )

        // Standard increment type player 1
        val standardIncrementTypePlayer1 = _timeConfigurationState.value.standardIncrementTypePlayer1

        // Standard increment type player 2
        val standardIncrementTypePlayer2 = _timeConfigurationState.value.standardIncrementTypePlayer2

        // Standard increment player 1
        val standardIncrementPlayer1 = parseDigitsToMillis(
            digitsHour = _timeConfigurationState.value.standardIncrementPlayer1HourState.currentItem,
            digitsMinute = _timeConfigurationState.value.standardIncrementPlayer1MinuteState.currentItem,
            digitsSecond = _timeConfigurationState.value.standardIncrementPlayer1SecondState.currentItem
        )

        // Standard increment player 2
        val standardIncrementPlayer2 = parseDigitsToMillis(
            digitsHour = _timeConfigurationState.value.standardIncrementPlayer2HourState.currentItem,
            digitsMinute = _timeConfigurationState.value.standardIncrementPlayer2MinuteState.currentItem,
            digitsSecond = _timeConfigurationState.value.standardIncrementPlayer2SecondState.currentItem
        )

        // First add on activated
        val firstAddOnActive = _timeConfigurationState.value.firstAddOnSetting

        // First add on move
        var firstAddOnMovePlayer1: Int? = null
        var firstAddOnMovePlayer2: Int? = null
        if (firstAddOnActive) {
            firstAddOnMovePlayer1 = try {
                _timeConfigurationState.value.firstAddOnMovePlayer1.toInt()
            } catch (e: Exception) {
                if (e is NumberFormatException) {
                    0
                } else {
                    throw e
                }
            }

            firstAddOnMovePlayer2 = try {
                _timeConfigurationState.value.firstAddOnMovePlayer2.toInt()
            } catch (e: Exception) {
                if (e is NumberFormatException) {
                    0
                } else {
                    throw e
                }
            }
        }

        // First add on time player 1
        val firstAddOnTimePlayer1 = parseDigitsToMillis(
            digitsHour = _timeConfigurationState.value.firstAddOnTimePlayer1HourState.currentItem,
            digitsMinute = _timeConfigurationState.value.firstAddOnTimePlayer1MinuteState.currentItem,
            digitsSecond = _timeConfigurationState.value.firstAddOnTimePlayer1SecondState.currentItem
        )

        // First add on time player 2
        val firstAddOnTimePlayer2 = parseDigitsToMillis(
            digitsHour = _timeConfigurationState.value.firstAddOnTimePlayer2HourState.currentItem,
            digitsMinute = _timeConfigurationState.value.firstAddOnTimePlayer2MinuteState.currentItem,
            digitsSecond = _timeConfigurationState.value.firstAddOnTimePlayer2SecondState.currentItem
        )

        // First add on increment type player 1
        val firstAddOnIncrementTypePlayer1 = _timeConfigurationState.value.firstAddOnIncrementTypePlayer1

        // First add on increment type player 2
        val firstAddOnIncrementTypePlayer2 = _timeConfigurationState.value.firstAddOnIncrementTypePlayer2

        // First add on increment player 1
        val firstAddOnIncrementPlayer1 = parseDigitsToMillis(
            digitsHour = _timeConfigurationState.value.firstAddOnIncrementPlayer1HourState.currentItem,
            digitsMinute = _timeConfigurationState.value.firstAddOnIncrementPlayer1MinuteState.currentItem,
            digitsSecond = _timeConfigurationState.value.firstAddOnIncrementPlayer1SecondState.currentItem
        )

        // First add on increment player 2
        val firstAddOnIncrementPlayer2 = parseDigitsToMillis(
            digitsHour = _timeConfigurationState.value.firstAddOnIncrementPlayer2HourState.currentItem,
            digitsMinute = _timeConfigurationState.value.firstAddOnIncrementPlayer2MinuteState.currentItem,
            digitsSecond = _timeConfigurationState.value.firstAddOnIncrementPlayer2SecondState.currentItem
        )

        // Second add on activated
        val secondAddOnActive = _timeConfigurationState.value.secondAddOnSetting

        // Second add on move
        var secondAddOnMovePlayer1: Int? = null
        var secondAddOnMovePlayer2: Int? = null
        if (firstAddOnActive && secondAddOnActive) {
            secondAddOnMovePlayer1 = try {
                _timeConfigurationState.value.secondAddOnMovePlayer1.toInt()
            } catch (e: Exception) {
                if (e is NumberFormatException) {
                    0
                } else {
                    throw e
                }
            }

            secondAddOnMovePlayer2 = try{
                _timeConfigurationState.value.secondAddOnMovePlayer2.toInt()
            } catch (e: Exception) {
                if (e is NumberFormatException) {
                    0
                } else {
                    throw e
                }
            }
        }

        // Second add on time player 1
        val secondAddOnTimePlayer1 = parseDigitsToMillis(
            digitsHour = _timeConfigurationState.value.secondAddOnTimePlayer1HourState.currentItem,
            digitsMinute = _timeConfigurationState.value.secondAddOnTimePlayer1MinuteState.currentItem,
            digitsSecond = _timeConfigurationState.value.secondAddOnTimePlayer1SecondState.currentItem
        )

        // Second add on time player 2
        val secondAddOnTimePlayer2 = parseDigitsToMillis(
            digitsHour = _timeConfigurationState.value.secondAddOnTimePlayer2HourState.currentItem,
            digitsMinute = _timeConfigurationState.value.secondAddOnTimePlayer2MinuteState.currentItem,
            digitsSecond = _timeConfigurationState.value.secondAddOnTimePlayer2SecondState.currentItem
        )

        // Second add on increment type player 1
        val secondAddOnIncrementTypePlayer1 = _timeConfigurationState.value.secondAddOnIncrementTypePlayer1

        // Second add on increment type player 2
        val secondAddOnIncrementTypePlayer2 = _timeConfigurationState.value.secondAddOnIncrementTypePlayer2

        // Second add on increment player 1
        val secondAddOnIncrementPlayer1 = parseDigitsToMillis(
            digitsHour = _timeConfigurationState.value.secondAddOnIncrementPlayer1HourState.currentItem,
            digitsMinute = _timeConfigurationState.value.secondAddOnIncrementPlayer1MinuteState.currentItem,
            digitsSecond = _timeConfigurationState.value.secondAddOnIncrementPlayer1SecondState.currentItem
        )

        // Second add on increment player 2
        val secondAddOnIncrementPlayer2 = parseDigitsToMillis(
            digitsHour = _timeConfigurationState.value.secondAddOnIncrementPlayer2HourState.currentItem,
            digitsMinute = _timeConfigurationState.value.secondAddOnIncrementPlayer2MinuteState.currentItem,
            digitsSecond = _timeConfigurationState.value.secondAddOnIncrementPlayer2SecondState.currentItem
        )

        // Check standard time
        if (standardTimePlayer1 <= 0 || standardTimePlayer2 <= 0) {
            return TimeConfigurationStatus.StandardTimeIssue
        }

        // Check standard increment
        if ((standardIncrementTypePlayer1 != IncrementType.None && standardIncrementPlayer1 <= 0) ||
            (standardIncrementTypePlayer2 != IncrementType.None && standardIncrementPlayer2 <= 0)
        ) {
            return TimeConfigurationStatus.StandardIncrementIssue
        }

        // Check first add on if active
        if (firstAddOnActive) {
            // Check first add on move
            if ((firstAddOnMovePlayer1!! < 1) || (firstAddOnMovePlayer1 > 999) || (firstAddOnMovePlayer2!! < 1) || (firstAddOnMovePlayer2 > 999)) {
                return TimeConfigurationStatus.FirstAddOnMoveIssue
            }
            // Check first add on time
            if (firstAddOnTimePlayer1 <= 0 || firstAddOnTimePlayer2 <= 0) {
                return TimeConfigurationStatus.FirstAddOnTimeIssue
            }
            // Check first add on increment
            if ((firstAddOnIncrementTypePlayer1 != IncrementType.None && firstAddOnIncrementPlayer1 <= 0) ||
                (firstAddOnIncrementTypePlayer2 != IncrementType.None && firstAddOnIncrementPlayer2 <= 0)
            ) {
                return TimeConfigurationStatus.FirstAddOnIncrementIssue
            }
        }

        // Check second add on if active
        if (secondAddOnActive) {
            // Check second add on move
            if ((secondAddOnMovePlayer1!! < 1) || (secondAddOnMovePlayer1 > 999) || (secondAddOnMovePlayer2!! < 1) || (secondAddOnMovePlayer2 > 999)) {
                return TimeConfigurationStatus.SecondAddOnMoveIssue
            }
            // Check second add on time
            if (secondAddOnTimePlayer1 <= 0 || secondAddOnTimePlayer2 <= 0) {
                return TimeConfigurationStatus.SecondAddOnTimeIssue
            }
            // Check second add on increment
            if ((secondAddOnIncrementTypePlayer1 != IncrementType.None && secondAddOnIncrementPlayer1 <= 0) ||
                (secondAddOnIncrementTypePlayer2 != IncrementType.None && secondAddOnIncrementPlayer2 <= 0)
            ) {
                return TimeConfigurationStatus.SecondAddOnIncrementIssue
            }
        }

        // Construct new game if all checks passed
        var newGame = PresetGame(
            standardTimePlayer1 = standardTimePlayer1,
            standardTimePlayer2 = standardTimePlayer2,
            standardIncrementTypePlayer1 = standardIncrementTypePlayer1,
            standardIncrementTypePlayer2 = standardIncrementTypePlayer2,
            standardIncrementPlayer1 = standardIncrementPlayer1,
            standardIncrementPlayer2 = standardIncrementPlayer2
        )

        if (firstAddOnActive) {
            newGame = newGame.copy(
                firstAddOnMovePlayer1 = firstAddOnMovePlayer1,
                firstAddOnMovePlayer2 = firstAddOnMovePlayer2,
                firstAddOnTimePlayer1 = firstAddOnTimePlayer1,
                firstAddOnTimePlayer2 = firstAddOnTimePlayer2,
                firstAddOnIncrementTypePlayer1 = firstAddOnIncrementTypePlayer1,
                firstAddOnIncrementTypePlayer2 = firstAddOnIncrementTypePlayer2,
                firstAddOnIncrementPlayer1 = firstAddOnIncrementPlayer1,
                firstAddOnIncrementPlayer2 = firstAddOnIncrementPlayer2
            )

            if (secondAddOnActive) {
                newGame = newGame.copy(
                    secondAddOnMovePlayer1 = secondAddOnMovePlayer1,
                    secondAddOnMovePlayer2 = secondAddOnMovePlayer2,
                    secondAddOnTimePlayer1 = secondAddOnTimePlayer1,
                    secondAddOnTimePlayer2 = secondAddOnTimePlayer2,
                    secondAddOnIncrementTypePlayer1 = secondAddOnIncrementTypePlayer1,
                    secondAddOnIncrementTypePlayer2 = secondAddOnIncrementTypePlayer2,
                    secondAddOnIncrementPlayer1 = secondAddOnIncrementPlayer1,
                    secondAddOnIncrementPlayer2 = secondAddOnIncrementPlayer2
                )
            }
        }

        // Try to add new game to database
        val id = presetGamesRepository.insertPresetGame(newGame)
        Log.d("TimeConfigurationViewModel", "New game id: $id")
        return if (id == null) {
            TimeConfigurationStatus.SavingError
        } else {
            preferencesRepository.setSelectedGame(id)
            chessClockRepository.reset()
            TimeConfigurationStatus.SavedSuccessfully
        }
    }
}
package eu.merklaafe.chessclockdigital.presentation.screens.timeconfiguration

import eu.merklaafe.chessclockdigital.model.IncrementType
import eu.merklaafe.chessclockdigital.model.Player

sealed class TimeConfigurationEvent {
    data class DifferentPlayerSettings(val value: Boolean): TimeConfigurationEvent()

    data class StandardIncrementTypeUpdate(val player: Player, val incrementType: IncrementType): TimeConfigurationEvent()

    data class FirstAddOnSetting(val value: Boolean): TimeConfigurationEvent()
    data class FirstAddOnMoveUpdate(val player: Player, val move: String): TimeConfigurationEvent()
    data class FirstAddOnIncrementTypeUpdate(val player: Player, val incrementType: IncrementType): TimeConfigurationEvent()

    data class SecondAddOnSetting(val value: Boolean): TimeConfigurationEvent()
    data class SecondAddOnMoveUpdate(val player: Player, val move: String): TimeConfigurationEvent()
    data class SecondAddOnIncrementTypeUpdate(val player: Player, val incrementType: IncrementType): TimeConfigurationEvent()

    data class Save(
        val onSuccess: () -> Unit
    ): TimeConfigurationEvent()

    data object SnackbarDismissed : TimeConfigurationEvent()
}
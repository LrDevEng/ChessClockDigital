package eu.merklaafe.chessclockdigital.presentation.screens.timeconfiguration

import eu.merklaafe.chessclockdigital.model.IncrementType
import eu.merklaafe.chessclockdigital.presentation.generic.ScrollWheelState

data class TimeConfigurationState (
    val snackbarMessage: String = "",

    val differentPlayerSettings: Boolean = false,
    val firstAddOnSetting: Boolean = false,
    val secondAddOnSetting: Boolean = false,

    val standardIncrementTypePlayer1: IncrementType = IncrementType.None,
    val standardIncrementTypePlayer2: IncrementType = IncrementType.None,
    val firstAddOnIncrementTypePlayer1: IncrementType = IncrementType.None,
    val firstAddOnIncrementTypePlayer2: IncrementType = IncrementType.None,
    val secondAddOnIncrementTypePlayer1: IncrementType = IncrementType.None,
    val secondAddOnIncrementTypePlayer2: IncrementType = IncrementType.None,

    val firstAddOnMovePlayer1: String = "",
    val firstAddOnMovePlayer2: String = "",
    val secondAddOnMovePlayer1: String = "",
    val secondAddOnMovePlayer2: String = "",

    val standardTimePlayer1HourState: ScrollWheelState = ScrollWheelState("0"),
    val standardTimePlayer1MinuteState: ScrollWheelState = ScrollWheelState("00"),
    val standardTimePlayer1SecondState: ScrollWheelState = ScrollWheelState("00"),
    val standardTimePlayer2HourState: ScrollWheelState = ScrollWheelState("0"),
    val standardTimePlayer2MinuteState: ScrollWheelState = ScrollWheelState("00"),
    val standardTimePlayer2SecondState: ScrollWheelState = ScrollWheelState("00"),
    val standardIncrementPlayer1HourState: ScrollWheelState = ScrollWheelState("0"),
    val standardIncrementPlayer1MinuteState: ScrollWheelState = ScrollWheelState("00"),
    val standardIncrementPlayer1SecondState: ScrollWheelState = ScrollWheelState("00"),
    val standardIncrementPlayer2HourState: ScrollWheelState = ScrollWheelState("0"),
    val standardIncrementPlayer2MinuteState: ScrollWheelState = ScrollWheelState("00"),
    val standardIncrementPlayer2SecondState: ScrollWheelState = ScrollWheelState("00"),

    val firstAddOnTimePlayer1HourState: ScrollWheelState = ScrollWheelState("0"),
    val firstAddOnTimePlayer1MinuteState: ScrollWheelState = ScrollWheelState("00"),
    val firstAddOnTimePlayer1SecondState: ScrollWheelState = ScrollWheelState("00"),
    val firstAddOnTimePlayer2HourState: ScrollWheelState = ScrollWheelState("0"),
    val firstAddOnTimePlayer2MinuteState: ScrollWheelState = ScrollWheelState("00"),
    val firstAddOnTimePlayer2SecondState: ScrollWheelState = ScrollWheelState("00"),
    val firstAddOnIncrementPlayer1HourState: ScrollWheelState = ScrollWheelState("0"),
    val firstAddOnIncrementPlayer1MinuteState: ScrollWheelState = ScrollWheelState("00"),
    val firstAddOnIncrementPlayer1SecondState: ScrollWheelState = ScrollWheelState("00"),
    val firstAddOnIncrementPlayer2HourState: ScrollWheelState = ScrollWheelState("0"),
    val firstAddOnIncrementPlayer2MinuteState: ScrollWheelState = ScrollWheelState("00"),
    val firstAddOnIncrementPlayer2SecondState: ScrollWheelState = ScrollWheelState("00"),

    val secondAddOnTimePlayer1HourState: ScrollWheelState = ScrollWheelState("0"),
    val secondAddOnTimePlayer1MinuteState: ScrollWheelState = ScrollWheelState("00"),
    val secondAddOnTimePlayer1SecondState: ScrollWheelState = ScrollWheelState("00"),
    val secondAddOnTimePlayer2HourState: ScrollWheelState = ScrollWheelState("0"),
    val secondAddOnTimePlayer2MinuteState: ScrollWheelState = ScrollWheelState("00"),
    val secondAddOnTimePlayer2SecondState: ScrollWheelState = ScrollWheelState("00"),
    val secondAddOnIncrementPlayer1HourState: ScrollWheelState = ScrollWheelState("0"),
    val secondAddOnIncrementPlayer1MinuteState: ScrollWheelState = ScrollWheelState("00"),
    val secondAddOnIncrementPlayer1SecondState: ScrollWheelState = ScrollWheelState("00"),
    val secondAddOnIncrementPlayer2HourState: ScrollWheelState = ScrollWheelState("0"),
    val secondAddOnIncrementPlayer2MinuteState: ScrollWheelState = ScrollWheelState("00"),
    val secondAddOnIncrementPlayer2SecondState: ScrollWheelState = ScrollWheelState("00"),
)
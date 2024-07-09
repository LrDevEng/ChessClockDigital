package eu.merklaafe.chessclockdigital.presentation.screens.timeconfiguration

import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import eu.merklaafe.chessclockdigital.model.IncrementType
import eu.merklaafe.chessclockdigital.presentation.generic.IncrementTypeDropdownMenu
import eu.merklaafe.chessclockdigital.presentation.generic.ScrollWheelState
import eu.merklaafe.chessclockdigital.presentation.generic.TimeInputField
import eu.merklaafe.chessclockdigital.util.Constants.digitFamily

@Composable
fun TimeConfigurationInput(
    timePlayer1HourState: ScrollWheelState,
    timePlayer1MinuteState: ScrollWheelState,
    timePlayer1SecondState: ScrollWheelState,
    timePlayer2HourState: ScrollWheelState,
    timePlayer2MinuteState: ScrollWheelState,
    timePlayer2SecondState: ScrollWheelState,
    incrementPlayer1HourState: ScrollWheelState,
    incrementPlayer1MinuteState: ScrollWheelState,
    incrementPlayer1SecondState: ScrollWheelState,
    incrementPlayer2HourState: ScrollWheelState,
    incrementPlayer2MinuteState: ScrollWheelState,
    incrementPlayer2SecondState: ScrollWheelState,
    movesPlayer1: String,
    movesPlayer2: String,
    incrementTypePlayer1: IncrementType,
    incrementTypePlayer2: IncrementType,
    onMovesPlayer1Updated: (String) -> Unit,
    onMovesPlayer2Updated: (String) -> Unit,
    onIncrementTypePlayer1Updated: (IncrementType) -> Unit,
    onIncrementTypePlayer2Updated: (IncrementType) -> Unit,
    showPlayer2: Boolean = false,
    showMoves: Boolean = true,
    textColor: Color = MaterialTheme.colorScheme.primary
) {
    var slideInOffset  by remember { mutableIntStateOf(0) }
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        // Moves
        if (showMoves) {
            Text(
                modifier = Modifier.padding(bottom = 5.dp),
                text = "After:",
                color = textColor,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
            Row(
                modifier = Modifier.padding(bottom = 5.dp),
            ) {
                Box(
                    modifier = Modifier
                        .padding(end = 5.dp)
                        .weight(1f)
                ) {
                    TextField(
                        modifier = Modifier
                            .fillMaxWidth(),
                        value = movesPlayer1,
                        placeholder = {
                            Text(
                                text = "1",
                                fontFamily = digitFamily,
                                overflow = TextOverflow.Ellipsis,
                                maxLines = 1
                            )
                        },
                        suffix = {
                            if (movesPlayer1.isEmpty() || movesPlayer1 == "1")
                                Text(
                                    text = "Move",
                                    overflow = TextOverflow.Ellipsis,
                                    maxLines = 1
                                )
                            else
                                Text(
                                    text = "Moves",
                                    overflow = TextOverflow.Ellipsis,
                                    maxLines = 1
                                )
                        },
                        onValueChange = {
                            onMovesPlayer1Updated(it)
                        },
                        maxLines = 1,
                        textStyle = LocalTextStyle.current.copy(
                            fontFamily = digitFamily
                        ),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.NumberPassword,
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = { focusManager.clearFocus() }
                        ),
                        colors = TextFieldDefaults.colors(
                            focusedTextColor = textColor,
                            unfocusedTextColor = textColor,
                            focusedSuffixColor = if (movesPlayer1.isEmpty()) TextFieldDefaults.colors().focusedSuffixColor else textColor,
                            unfocusedSuffixColor = if (movesPlayer1.isEmpty()) TextFieldDefaults.colors().unfocusedSuffixColor else textColor
                        )
                    )
                }
                Box(
                    modifier = Modifier
                        .padding(start = 5.dp)
                        .weight(1f)
                        .onGloballyPositioned {
                            slideInOffset = it.size.width
                        }
                ) {
                    androidx.compose.animation.AnimatedVisibility(
                        visible = showPlayer2,
                        enter = slideInHorizontally(initialOffsetX = { slideInOffset }),
                        exit = slideOutHorizontally(targetOffsetX = { slideInOffset })
                    ) {
                        TextField(
                            modifier = Modifier
                                .fillMaxWidth(),
                            value = movesPlayer2,
                            placeholder = {
                                Text(
                                    text = "1",
                                    fontFamily = digitFamily,
                                    overflow = TextOverflow.Ellipsis,
                                    maxLines = 1
                                )
                            },
                            suffix = {
                                if (movesPlayer2.isEmpty() || movesPlayer2 == "1")
                                    Text(
                                        text = "Move",
                                        overflow = TextOverflow.Ellipsis,
                                        maxLines = 1
                                    )
                                else
                                    Text(
                                        text = "Moves",
                                        overflow = TextOverflow.Ellipsis,
                                        maxLines = 1
                                    )
                            },
                            onValueChange = {
                                  onMovesPlayer2Updated(it)
                            },
                            maxLines = 1,
                            textStyle = LocalTextStyle.current.copy(
                                fontFamily = digitFamily
                            ),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.NumberPassword,
                                imeAction = ImeAction.Done
                            ),
                            keyboardActions = KeyboardActions(
                                onDone = { focusManager.clearFocus() }
                            ),
                            colors = TextFieldDefaults.colors(
                                focusedTextColor = textColor,
                                unfocusedTextColor = textColor,
                                focusedSuffixColor = if (movesPlayer1.isEmpty()) TextFieldDefaults.colors().focusedSuffixColor else textColor,
                                unfocusedSuffixColor = if (movesPlayer1.isEmpty()) TextFieldDefaults.colors().unfocusedSuffixColor else textColor
                            )
                        )
                    }
                }
            }
        }


        // Time
        Text(
            modifier = Modifier.padding(bottom = 5.dp),
            text = "Initial Time:",
            color = textColor,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
        Row(
            modifier = Modifier.padding(bottom = 5.dp)
        ) {
            Box(
                modifier = Modifier
                    .padding(end = 5.dp)
                    .weight(1f)
            ) {
                TimeInputField(
                    hourState = timePlayer1HourState,
                    minuteState = timePlayer1MinuteState,
                    secondState = timePlayer1SecondState,
                    textColor = textColor
                )
            }
            Box(
                modifier = Modifier
                    .padding(start = 5.dp)
                    .weight(1f)
                    .onGloballyPositioned {
                        slideInOffset = it.size.width
                    }
            ) {
                androidx.compose.animation.AnimatedVisibility(
                    visible = showPlayer2,
                    enter = slideInHorizontally(initialOffsetX = {slideInOffset}),
                    exit = slideOutHorizontally(targetOffsetX = {slideInOffset})
                ) {
                    TimeInputField(
                        hourState = timePlayer2HourState,
                        minuteState = timePlayer2MinuteState,
                        secondState = timePlayer2SecondState,
                        textColor = textColor
                    )
                }
            }
        }

        // Increment
        Text(
            modifier = Modifier.padding(bottom = 5.dp),
            text = "Increment:",
            color = textColor,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
        Row(
            modifier = Modifier.padding(bottom = 5.dp)
        ) {
            Box(
                modifier = Modifier
                    .padding(end = 5.dp)
                    .weight(1f)
            ) {
                Column {
                    IncrementTypeDropdownMenu(
                        incrementType = incrementTypePlayer1,
                        itemSelected = {
                            onIncrementTypePlayer1Updated(it)
                        }
                    )
                    androidx.compose.animation.AnimatedVisibility(
                        visible = incrementTypePlayer1 != IncrementType.None,
                        enter = slideInVertically(),
                        exit = fadeOut()
                    ) {
                        TimeInputField(
                            hourState = incrementPlayer1HourState,
                            minuteState = incrementPlayer1MinuteState,
                            secondState = incrementPlayer1SecondState,
                            rangeMinute = (0..29),
                            modifier = Modifier.padding(top = 5.dp),
                            showHour = false,
                            textColor = textColor
                        )
                    }
                }
            }
            Box(
                modifier = Modifier
                    .padding(start = 5.dp)
                    .weight(1f)
                    .onGloballyPositioned {
                        slideInOffset = it.size.width
                    }
            ) {
                androidx.compose.animation.AnimatedVisibility(
                    visible = showPlayer2,
                    enter = slideInHorizontally(initialOffsetX = {slideInOffset}),
                    exit = slideOutHorizontally(targetOffsetX = {slideInOffset})
                ) {
                    Column {
                        IncrementTypeDropdownMenu(
                            incrementType = incrementTypePlayer2,
                            itemSelected = {
                                onIncrementTypePlayer2Updated(it)
                            }
                        )
                        androidx.compose.animation.AnimatedVisibility(
                            visible = incrementTypePlayer2 != IncrementType.None,
                            enter = slideInVertically(),
                            exit = fadeOut()
                        ) {
                            TimeInputField(
                                hourState = incrementPlayer2HourState,
                                minuteState = incrementPlayer2MinuteState,
                                secondState = incrementPlayer2SecondState,
                                rangeMinute = (0..29),
                                modifier = Modifier.padding(top = 5.dp),
                                showHour = false,
                                textColor = textColor
                            )
                        }
                    }
                }
            }
        }
    }
}
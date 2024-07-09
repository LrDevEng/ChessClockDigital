package eu.merklaafe.chessclockdigital.presentation.screens.timeconfiguration

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import eu.merklaafe.chessclockdigital.R
import eu.merklaafe.chessclockdigital.model.IncrementType
import eu.merklaafe.chessclockdigital.presentation.generic.ScrollWheelState
import kotlinx.coroutines.launch

@Composable
fun TimeConfigurationScreen (
    snackbarMessage: String,

    standardTimePlayer1HourState: ScrollWheelState,
    standardTimePlayer1MinuteState: ScrollWheelState,
    standardTimePlayer1SecondState: ScrollWheelState,
    standardTimePlayer2HourState: ScrollWheelState,
    standardTimePlayer2MinuteState: ScrollWheelState,
    standardTimePlayer2SecondState: ScrollWheelState,
    standardIncrementPlayer1HourState: ScrollWheelState,
    standardIncrementPlayer1MinuteState: ScrollWheelState,
    standardIncrementPlayer1SecondState: ScrollWheelState,
    standardIncrementPlayer2HourState: ScrollWheelState,
    standardIncrementPlayer2MinuteState: ScrollWheelState,
    standardIncrementPlayer2SecondState: ScrollWheelState,
    standardIncrementTypePlayer1: IncrementType,
    standardIncrementTypePlayer2: IncrementType,
    onStandardIncrementTypePlayer1Updated: (IncrementType) -> Unit,
    onStandardIncrementTypePlayer2Updated: (IncrementType) -> Unit,

    firstAddOnTimePlayer1HourState: ScrollWheelState,
    firstAddOnTimePlayer1MinuteState: ScrollWheelState,
    firstAddOnTimePlayer1SecondState: ScrollWheelState,
    firstAddOnTimePlayer2HourState: ScrollWheelState,
    firstAddOnTimePlayer2MinuteState: ScrollWheelState,
    firstAddOnTimePlayer2SecondState: ScrollWheelState,
    firstAddOnIncrementPlayer1HourState: ScrollWheelState,
    firstAddOnIncrementPlayer1MinuteState: ScrollWheelState,
    firstAddOnIncrementPlayer1SecondState: ScrollWheelState,
    firstAddOnIncrementPlayer2HourState: ScrollWheelState,
    firstAddOnIncrementPlayer2MinuteState: ScrollWheelState,
    firstAddOnIncrementPlayer2SecondState: ScrollWheelState,
    firstAddOnMovesPlayer1: String,
    firstAddOnMovesPlayer2: String,
    firstAddOnIncrementTypePlayer1: IncrementType,
    firstAddOnIncrementTypePlayer2: IncrementType,
    onFirstAddOnMovesPlayer1Updated: (String) -> Unit,
    onFirstAddOnMovesPlayer2Updated: (String) -> Unit,
    onFirstAddOnIncrementTypePlayer1Updated: (IncrementType) -> Unit,
    onFirstAddOnIncrementTypePlayer2Updated: (IncrementType) -> Unit,

    secondAddOnTimePlayer1HourState: ScrollWheelState,
    secondAddOnTimePlayer1MinuteState: ScrollWheelState,
    secondAddOnTimePlayer1SecondState: ScrollWheelState,
    secondAddOnTimePlayer2HourState: ScrollWheelState,
    secondAddOnTimePlayer2MinuteState: ScrollWheelState,
    secondAddOnTimePlayer2SecondState: ScrollWheelState,
    secondAddOnIncrementPlayer1HourState: ScrollWheelState,
    secondAddOnIncrementPlayer1MinuteState: ScrollWheelState,
    secondAddOnIncrementPlayer1SecondState: ScrollWheelState,
    secondAddOnIncrementPlayer2HourState: ScrollWheelState,
    secondAddOnIncrementPlayer2MinuteState: ScrollWheelState,
    secondAddOnIncrementPlayer2SecondState: ScrollWheelState,
    secondAddOnMovesPlayer1: String,
    secondAddOnMovesPlayer2: String,
    secondAddOnIncrementTypePlayer1: IncrementType,
    secondAddOnIncrementTypePlayer2: IncrementType,
    onSecondAddOnMovesPlayer1Updated: (String) -> Unit,
    onSecondAddOnMovesPlayer2Updated: (String) -> Unit,
    onSecondAddOnIncrementTypePlayer1Updated: (IncrementType) -> Unit,
    onSecondAddOnIncrementTypePlayer2Updated: (IncrementType) -> Unit,

    showPlayer2Content: Boolean,
    onShowPlayer2ContentChange: (Boolean) -> Unit,

    showAddOn1Content: Boolean,
    onShowAddOn1ContentChange: (Boolean) -> Unit,

    showAddOn2Content: Boolean,
    onShowAddOn2ContentChange: (Boolean) -> Unit,

    onSaveClick: () -> Unit,
    onBackClick: () -> Unit,
    onSnackbarDismissed: () -> Unit
) {
    val focusManager = LocalFocusManager.current
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = snackbarMessage) {
        if (snackbarMessage.isNotEmpty()) {
            val result = snackbarHostState.showSnackbar(
                message = snackbarMessage
            )
            if (result == SnackbarResult.Dismissed) {
                onSnackbarDismissed()
            }
        }
    }

    Scaffold (
        modifier = Modifier
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        focusManager.clearFocus()
                    }
                )
            },
        topBar = {
            TimeConfigurationTopBar(
                onBackClick = onBackClick
            )
        },
        bottomBar = {
            Button(
                modifier = Modifier
                    .padding(start = 10.dp, end = 10.dp, bottom = 10.dp, top = 5.dp)
                    .background(color = Color.Transparent)
                    .fillMaxWidth()
                    .height(48.dp),
                onClick = onSaveClick,
                colors = ButtonDefaults.buttonColors(
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = Modifier.fillMaxHeight(),
                        painter = painterResource(id = R.drawable.baseline_save_24),
                        contentDescription = "Save"
                    )
                    Spacer(modifier = Modifier
                        .fillMaxHeight()
                        .width(5.dp))
                    Text(
                        text = "Save",
                        style = MaterialTheme.typography.titleLarge
                    )
                }

            }
        },
        content = {
            TimeConfigurationContent(
                paddingValues = it,
                standardTimePlayer1HourState = standardTimePlayer1HourState,
                standardTimePlayer1MinuteState = standardTimePlayer1MinuteState,
                standardTimePlayer1SecondState = standardTimePlayer1SecondState,
                standardTimePlayer2HourState = standardTimePlayer2HourState,
                standardTimePlayer2MinuteState = standardTimePlayer2MinuteState,
                standardTimePlayer2SecondState = standardTimePlayer2SecondState,
                standardIncrementPlayer1HourState = standardIncrementPlayer1HourState,
                standardIncrementPlayer1MinuteState = standardIncrementPlayer1MinuteState,
                standardIncrementPlayer1SecondState = standardIncrementPlayer1SecondState,
                standardIncrementPlayer2HourState = standardIncrementPlayer2HourState,
                standardIncrementPlayer2MinuteState = standardIncrementPlayer2MinuteState,
                standardIncrementPlayer2SecondState = standardIncrementPlayer2SecondState,
                standardIncrementTypePlayer1 = standardIncrementTypePlayer1,
                standardIncrementTypePlayer2 = standardIncrementTypePlayer2,
                onStandardIncrementTypePlayer1Updated = onStandardIncrementTypePlayer1Updated,
                onStandardIncrementTypePlayer2Updated = onStandardIncrementTypePlayer2Updated,
                firstAddOnTimePlayer1HourState = firstAddOnTimePlayer1HourState,
                firstAddOnTimePlayer1MinuteState = firstAddOnTimePlayer1MinuteState,
                firstAddOnTimePlayer1SecondState = firstAddOnTimePlayer1SecondState,
                firstAddOnTimePlayer2HourState = firstAddOnTimePlayer2HourState,
                firstAddOnTimePlayer2MinuteState = firstAddOnTimePlayer2MinuteState,
                firstAddOnTimePlayer2SecondState = firstAddOnTimePlayer2SecondState,
                firstAddOnIncrementPlayer1HourState = firstAddOnIncrementPlayer1HourState,
                firstAddOnIncrementPlayer1MinuteState = firstAddOnIncrementPlayer1MinuteState,
                firstAddOnIncrementPlayer1SecondState = firstAddOnIncrementPlayer1SecondState,
                firstAddOnIncrementPlayer2HourState = firstAddOnIncrementPlayer2HourState,
                firstAddOnIncrementPlayer2MinuteState = firstAddOnIncrementPlayer2MinuteState,
                firstAddOnIncrementPlayer2SecondState = firstAddOnIncrementPlayer2SecondState,
                firstAddOnMovesPlayer1 = firstAddOnMovesPlayer1,
                firstAddOnMovesPlayer2 = firstAddOnMovesPlayer2,
                firstAddOnIncrementTypePlayer1 = firstAddOnIncrementTypePlayer1,
                firstAddOnIncrementTypePlayer2 = firstAddOnIncrementTypePlayer2,
                onFirstAddOnMovesPlayer1Updated = onFirstAddOnMovesPlayer1Updated,
                onFirstAddOnMovesPlayer2Updated = onFirstAddOnMovesPlayer2Updated,
                onFirstAddOnIncrementTypePlayer1Updated = onFirstAddOnIncrementTypePlayer1Updated,
                onFirstAddOnIncrementTypePlayer2Updated = onFirstAddOnIncrementTypePlayer2Updated,
                secondAddOnTimePlayer1HourState = secondAddOnTimePlayer1HourState,
                secondAddOnTimePlayer1MinuteState = secondAddOnTimePlayer1MinuteState,
                secondAddOnTimePlayer1SecondState = secondAddOnTimePlayer1SecondState,
                secondAddOnTimePlayer2HourState = secondAddOnTimePlayer2HourState,
                secondAddOnTimePlayer2MinuteState = secondAddOnTimePlayer2MinuteState,
                secondAddOnTimePlayer2SecondState = secondAddOnTimePlayer2SecondState,
                secondAddOnIncrementPlayer1HourState = secondAddOnIncrementPlayer1HourState,
                secondAddOnIncrementPlayer1MinuteState = secondAddOnIncrementPlayer1MinuteState,
                secondAddOnIncrementPlayer1SecondState = secondAddOnIncrementPlayer1SecondState,
                secondAddOnIncrementPlayer2HourState = secondAddOnIncrementPlayer2HourState,
                secondAddOnIncrementPlayer2MinuteState = secondAddOnIncrementPlayer2MinuteState,
                secondAddOnIncrementPlayer2SecondState = secondAddOnIncrementPlayer2SecondState,
                secondAddOnMovesPlayer1 = secondAddOnMovesPlayer1,
                secondAddOnMovesPlayer2 = secondAddOnMovesPlayer2,
                secondAddOnIncrementTypePlayer1 = secondAddOnIncrementTypePlayer1,
                secondAddOnIncrementTypePlayer2 = secondAddOnIncrementTypePlayer2,
                onSecondAddOnMovesPlayer1Updated = onSecondAddOnMovesPlayer1Updated,
                onSecondAddOnMovesPlayer2Updated = onSecondAddOnMovesPlayer2Updated,
                onSecondAddOnIncrementTypePlayer1Updated = onSecondAddOnIncrementTypePlayer1Updated,
                onSecondAddOnIncrementTypePlayer2Updated = onSecondAddOnIncrementTypePlayer2Updated,
                showPlayer2Content = showPlayer2Content,
                onShowPlayer2ContentChange = onShowPlayer2ContentChange,
                showAddOn1Content = showAddOn1Content,
                onShowAddOn1ContentChange = onShowAddOn1ContentChange,
                showAddOn2Content = showAddOn2Content,
                onShowAddOn2ContentChange = onShowAddOn2ContentChange
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    )
}
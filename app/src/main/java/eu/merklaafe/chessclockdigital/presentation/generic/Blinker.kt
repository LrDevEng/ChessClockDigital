package eu.merklaafe.chessclockdigital.presentation.generic

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun Blinker (
    content: @Composable () -> Unit,
    timeOn: Long = 1000,
    timeOff: Long = timeOn,
    enabled: Boolean = true
) {
    val scope = rememberCoroutineScope()
    var visible by remember { mutableStateOf(true) }

    LaunchedEffect(key1 = visible) {
        scope.launch {
            if (visible)
                delay(timeOn)
            else
                delay(timeOff)
            visible = !visible
        }
    }

    if (visible || !enabled) {
        content()
    }
}
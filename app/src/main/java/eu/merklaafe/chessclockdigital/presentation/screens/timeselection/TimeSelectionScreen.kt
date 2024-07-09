package eu.merklaafe.chessclockdigital.presentation.screens.timeselection

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import eu.merklaafe.chessclockdigital.R
import eu.merklaafe.chessclockdigital.data_source.database.entity.PresetGame
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimeSelectionScreen(
    presetGames: List<PresetGame>,
    selectedGameId: Int,
    showDeleteOption: Boolean,
    onBackClick: () -> Unit,
    onSetSelectionClick: (Int) -> Unit,
    onAddTimeControlClick: () -> Unit,
    onDeleteOptionChange: (Boolean) -> Unit,
    onDeleteItemClick: (Int) -> Unit
) {
    val preScrollOffsetHysteresis = 15

    var floatingActionButtonHeight by remember { mutableStateOf(1.dp) }
    var selectedRadioButtonId by remember { mutableIntStateOf(selectedGameId) }
    var bottomActionButtonsVisible by remember { mutableStateOf(true) }

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val localDensity = LocalDensity.current
    val lazyColumnState = rememberLazyListState()
    val lastItemVisible by remember { derivedStateOf { lazyColumnState.layoutInfo.visibleItemsInfo.any{it.index == lazyColumnState.layoutInfo.totalItemsCount-2}}}
    var lastItemVisibleFlank by remember { mutableStateOf(false) }
    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(
                available: Offset,
                source: NestedScrollSource
            ): Offset {
                if (bottomActionButtonsVisible && available.y < -preScrollOffsetHysteresis && !lastItemVisibleFlank) {
                    bottomActionButtonsVisible = false
                    // Log.d("NestedScrollConnection","onPreScroll triggered, Offset: ${available.y}")
                } else if (available.y > preScrollOffsetHysteresis) {
                    if (!lastItemVisible) {
                        lastItemVisibleFlank = false
                    }
                    // Log.d("NestedScrollConnection","onPreScroll triggered, Offset: ${available.y}, lastItemVisibleFlank set to false")
                    if (!bottomActionButtonsVisible) {
                        bottomActionButtonsVisible = true
                    }
                }
                return super.onPreScroll(available, source)
            }
        }
    }

    Log.d("TimeSelectionScreen", "Collected game id: $selectedGameId")

    // Show action buttons when selected radio button is updated
    LaunchedEffect( key1 = selectedRadioButtonId) {
        if (!bottomActionButtonsVisible) {
            bottomActionButtonsVisible = true
        }
    }

    // Show action buttons when last item gets visible
    LaunchedEffect( key1 = lastItemVisible) {
        Log.d("LazyColumnState","Last item visible: $lastItemVisible, flank: $lastItemVisibleFlank")
        if (!lastItemVisibleFlank && lastItemVisible) {
            lastItemVisibleFlank = true
        }
        if (!bottomActionButtonsVisible && lastItemVisibleFlank) {
            bottomActionButtonsVisible = true
        }
    }

    // Scroll list to end when last item gets visible
    LaunchedEffect( key1 = bottomActionButtonsVisible) {
        if (bottomActionButtonsVisible && lastItemVisibleFlank) {
            lazyColumnState.animateScrollToItem(lazyColumnState.layoutInfo.totalItemsCount)
        }
    }

    Scaffold (
        modifier = Modifier,
        topBar = {
            TimeSelectionTopAppBar(
                showDeleteOption = showDeleteOption,
                onBackClick = onBackClick,
                onDeleteItemsClick = {
                    if (presetGames.size <= 1) {
                        scope.launch {
                            val result = snackbarHostState.showSnackbar(
                                message = "Cannot delete last time control.",
                                actionLabel = "Add"
                            )
                            if (result == SnackbarResult.ActionPerformed) {
                                onAddTimeControlClick()
                            }
                        }
                    } else {
                        onDeleteOptionChange(true)
                    }
                },
                onAddItemClick = onAddTimeControlClick,
                onCloseClick = {
                    onDeleteOptionChange(false)
                }
            )
        },
        floatingActionButton = {
            AnimatedVisibility(
                visible = bottomActionButtonsVisible,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                FloatingActionButton(
                    modifier = Modifier
                        .onGloballyPositioned {
                            floatingActionButtonHeight = with(localDensity) { it.size.height.toDp() }
                        },
                    elevation = FloatingActionButtonDefaults.elevation(
                        defaultElevation = 0.dp,
                        focusedElevation = 0.dp,
                        hoveredElevation = 0.dp,
                        pressedElevation = 0.dp
                    ),
                    onClick = onAddTimeControlClick,
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add New Time Control"
                    )
                }
            }
        },
        content = {paddingValues ->
            TimeSelectionContent(
                paddingValues = paddingValues,
                presetGames = presetGames,
                showDeleteOption = showDeleteOption,
                floatingActionButtonHeight = floatingActionButtonHeight,
                nestedScrollConnection = nestedScrollConnection,
                lazyColumnState = lazyColumnState,
                selectedRadioButtonId = selectedRadioButtonId,
                onRadioButtonClick = {
                    selectedRadioButtonId = it
                    onSetSelectionClick(it)
                },
                onDeleteItemClick = onDeleteItemClick,
                onInfoButtonClick = {configInfo ->
                    scope.launch {
                        snackbarHostState.currentSnackbarData?.dismiss()
                        snackbarHostState.showSnackbar(
                            message = configInfo
                        )
                    }
                }
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    )
}


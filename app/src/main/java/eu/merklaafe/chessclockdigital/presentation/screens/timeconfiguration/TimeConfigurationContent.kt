package eu.merklaafe.chessclockdigital.presentation.screens.timeconfiguration

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import eu.merklaafe.chessclockdigital.model.IncrementType
import eu.merklaafe.chessclockdigital.presentation.generic.ScrollWheelState
import kotlinx.coroutines.launch
import kotlin.math.max

@OptIn(ExperimentalFoundationApi::class, ExperimentalAnimationApi::class)
@Composable
fun TimeConfigurationContent(
    paddingValues: PaddingValues,

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

    textColor: Color = MaterialTheme.colorScheme.primary
) {
    val lazyListState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    var heightLazyColum = 200
    var heightAddOnSwitch = 100
    var heightHeader = 100

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
            .navigationBarsPadding()
            .padding(
                top = paddingValues.calculateTopPadding(),
                bottom = paddingValues.calculateBottomPadding()
            )
            .onGloballyPositioned {
                heightLazyColum = it.size.height
            },
        state = lazyListState
    ) {
        // Player different option switch
        stickyHeader(
            key = 0
        ) {
            Surface {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp)
                        .onGloballyPositioned {
                            heightHeader = it.size.height
                        }
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            modifier = Modifier.weight(1f),
                            text = "Different for each player:",
                            color = textColor,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1,
                            fontWeight = FontWeight.SemiBold
                        )
                        Switch(
                            checked = showPlayer2Content,
                            onCheckedChange = { onShowPlayer2ContentChange(it) }
                        )
                    }
                    AnimatedVisibility(
                        visible = showPlayer2Content
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Box(
                                modifier = Modifier
                                    .padding(end = 5.dp)
                                    .weight(1f)
                            ) {
                                Text(
                                    text = "Player 1:",
                                    color = textColor,
                                    overflow = TextOverflow.Ellipsis,
                                    maxLines = 1
                                )
                            }
                            Box(
                                modifier = Modifier
                                    .padding(start = 5.dp)
                                    .weight(1f)
                            ) {
                                Text(
                                    text = "Player 2:",
                                    color = textColor,
                                    overflow = TextOverflow.Ellipsis,
                                    maxLines = 1
                                )
                            }

                        }
                    }
                    HorizontalDivider(
                        modifier = Modifier.padding(top = 5.dp),
                        thickness = 3.dp,
                        color = MaterialTheme.colorScheme.outline
                    )
                }
            }
        }

        // Game data
        item (
            key = 1
        ) {
            Column {

                // Standard game data
                TimeConfigurationInput(
                    timePlayer1HourState = standardTimePlayer1HourState,
                    timePlayer1MinuteState = standardTimePlayer1MinuteState,
                    timePlayer1SecondState = standardTimePlayer1SecondState,
                    timePlayer2HourState = standardTimePlayer2HourState,
                    timePlayer2MinuteState = standardTimePlayer2MinuteState,
                    timePlayer2SecondState = standardTimePlayer2SecondState,
                    incrementPlayer1HourState = standardIncrementPlayer1HourState,
                    incrementPlayer1MinuteState = standardIncrementPlayer1MinuteState,
                    incrementPlayer1SecondState = standardIncrementPlayer1SecondState,
                    incrementPlayer2HourState = standardIncrementPlayer2HourState,
                    incrementPlayer2MinuteState = standardIncrementPlayer2MinuteState,
                    incrementPlayer2SecondState = standardIncrementPlayer2SecondState,
                    movesPlayer1 = "1",
                    movesPlayer2 = "1",
                    incrementTypePlayer1 = standardIncrementTypePlayer1,
                    incrementTypePlayer2 = standardIncrementTypePlayer2,
                    onMovesPlayer1Updated = {},
                    onMovesPlayer2Updated = {},
                    onIncrementTypePlayer1Updated = onStandardIncrementTypePlayer1Updated,
                    onIncrementTypePlayer2Updated = onStandardIncrementTypePlayer2Updated,
                    showMoves = false,
                    showPlayer2 = showPlayer2Content
                )

                // First add on switch
                Column (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp)
                        .onGloballyPositioned {
                            heightAddOnSwitch = it.size.height
                        }
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            modifier = Modifier.weight(1f),
                            text = "Add on (1):",
                            color = textColor,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1,
                            fontWeight = FontWeight.SemiBold
                        )
                        Switch(
                            checked = showAddOn1Content,
                            onCheckedChange = { onShowAddOn1ContentChange(it) }
                        )
                    }
                    AnimatedVisibility(
                        visible = showAddOn1Content,
                        enter = slideInVertically() + fadeIn(),
                        exit = slideOutVertically() + fadeOut()
                    ) {
                        HorizontalDivider(
                            modifier = Modifier.padding(top = 5.dp),
                            thickness = 3.dp,
                            color = MaterialTheme.colorScheme.outline
                        )
                    }
                }

                // First add on data
                AnimatedVisibility(
                    visible = showAddOn1Content,
                    enter = slideInVertically(animationSpec = tween(200,easing = LinearOutSlowInEasing)) + fadeIn(animationSpec = tween(200, easing = LinearOutSlowInEasing)),
                    exit = slideOutVertically() + fadeOut()
                ) {
                    val transitionRunning by remember { mutableStateOf(this.transition.isRunning) }
                    LaunchedEffect(key1 = transitionRunning) {
                        if (!transitionRunning) {
                            val pixelsToScroll = max(heightLazyColum.toFloat() - heightAddOnSwitch.toFloat() - heightHeader.toFloat(), 0F)
                            scope.launch {
                                lazyListState.animateScrollBy(
                                    value = pixelsToScroll,
                                    animationSpec = tween(
                                        durationMillis = 500,
                                        easing = LinearOutSlowInEasing
                                    )
                                )
                            }
                        }
                    }

                    Column {
                        TimeConfigurationInput(
                            timePlayer1HourState = firstAddOnTimePlayer1HourState,
                            timePlayer1MinuteState = firstAddOnTimePlayer1MinuteState,
                            timePlayer1SecondState = firstAddOnTimePlayer1SecondState,
                            timePlayer2HourState = firstAddOnTimePlayer2HourState,
                            timePlayer2MinuteState = firstAddOnTimePlayer2MinuteState,
                            timePlayer2SecondState = firstAddOnTimePlayer2SecondState,
                            incrementPlayer1HourState = firstAddOnIncrementPlayer1HourState,
                            incrementPlayer1MinuteState = firstAddOnIncrementPlayer1MinuteState,
                            incrementPlayer1SecondState = firstAddOnIncrementPlayer1SecondState,
                            incrementPlayer2HourState = firstAddOnIncrementPlayer2HourState,
                            incrementPlayer2MinuteState = firstAddOnIncrementPlayer2MinuteState,
                            incrementPlayer2SecondState = firstAddOnIncrementPlayer2SecondState,
                            movesPlayer1 = firstAddOnMovesPlayer1,
                            movesPlayer2 = firstAddOnMovesPlayer2,
                            incrementTypePlayer1 = firstAddOnIncrementTypePlayer1,
                            incrementTypePlayer2 = firstAddOnIncrementTypePlayer2,
                            onMovesPlayer1Updated = onFirstAddOnMovesPlayer1Updated,
                            onMovesPlayer2Updated = onFirstAddOnMovesPlayer2Updated,
                            onIncrementTypePlayer1Updated = onFirstAddOnIncrementTypePlayer1Updated,
                            onIncrementTypePlayer2Updated = onFirstAddOnIncrementTypePlayer2Updated,
                            showPlayer2 = showPlayer2Content
                        )

                        // Second add on switch
                        Column (
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 10.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    modifier = Modifier.weight(1f),
                                    text = "Add on (2):",
                                    color = textColor,
                                    overflow = TextOverflow.Ellipsis,
                                    maxLines = 1,
                                    fontWeight = FontWeight.SemiBold
                                )
                                Switch(
                                    checked = showAddOn2Content,
                                    onCheckedChange = {
                                        onShowAddOn2ContentChange(it)

                                    }
                                )
                            }
                            AnimatedVisibility(
                                visible = showAddOn2Content,
                                enter = slideInVertically() + fadeIn(),
                                exit = slideOutVertically() + fadeOut()
                            ) {
                                HorizontalDivider(
                                    modifier = Modifier.padding(top = 5.dp),
                                    thickness = 3.dp,
                                    color = MaterialTheme.colorScheme.outline
                                )
                            }
                        }
                    }
                }

                // Second add on data
                AnimatedVisibility(
                    visible = showAddOn2Content,
                    enter = slideInVertically() + fadeIn(),
                    exit = slideOutVertically() + fadeOut()
                ) {
                    val transitionRunning by remember { mutableStateOf(this.transition.isRunning) }
                    LaunchedEffect(key1 = transitionRunning) {
                        if (!transitionRunning) {
                            val pixelsToScroll = max(heightLazyColum.toFloat() - heightAddOnSwitch.toFloat() - heightHeader.toFloat(), 0F)
                            scope.launch {
                                lazyListState.animateScrollBy(
                                    value = pixelsToScroll,
                                    animationSpec = tween(
                                        durationMillis = 500,
                                        easing = LinearOutSlowInEasing
                                    )
                                )
                            }
                        }
                    }

                    TimeConfigurationInput(
                        timePlayer1HourState = secondAddOnTimePlayer1HourState,
                        timePlayer1MinuteState = secondAddOnTimePlayer1MinuteState,
                        timePlayer1SecondState = secondAddOnTimePlayer1SecondState,
                        timePlayer2HourState = secondAddOnTimePlayer2HourState,
                        timePlayer2MinuteState = secondAddOnTimePlayer2MinuteState,
                        timePlayer2SecondState = secondAddOnTimePlayer2SecondState,
                        incrementPlayer1HourState = secondAddOnIncrementPlayer1HourState,
                        incrementPlayer1MinuteState = secondAddOnIncrementPlayer1MinuteState,
                        incrementPlayer1SecondState = secondAddOnIncrementPlayer1SecondState,
                        incrementPlayer2HourState = secondAddOnIncrementPlayer2HourState,
                        incrementPlayer2MinuteState = secondAddOnIncrementPlayer2MinuteState,
                        incrementPlayer2SecondState = secondAddOnIncrementPlayer2SecondState,
                        movesPlayer1 = secondAddOnMovesPlayer1,
                        movesPlayer2 = secondAddOnMovesPlayer2,
                        incrementTypePlayer1 = secondAddOnIncrementTypePlayer1,
                        incrementTypePlayer2 = secondAddOnIncrementTypePlayer2,
                        onMovesPlayer1Updated = onSecondAddOnMovesPlayer1Updated,
                        onMovesPlayer2Updated = onSecondAddOnMovesPlayer2Updated,
                        onIncrementTypePlayer1Updated = onSecondAddOnIncrementTypePlayer1Updated,
                        onIncrementTypePlayer2Updated = onSecondAddOnIncrementTypePlayer2Updated,
                        showPlayer2 = showPlayer2Content
                    )
                }
            }
        }

        // First add on switch
//        item(
//            key = 2
//        ) {
//            Column (
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(horizontal = 10.dp)
//            ) {
//                Row(
//                    modifier = Modifier.fillMaxWidth(),
//                    verticalAlignment = Alignment.CenterVertically,
//                    horizontalArrangement = Arrangement.SpaceBetween
//                ) {
//                    Text("Add on (1):")
//                    Switch(
//                        checked = showAddOn1Content,
//                        onCheckedChange = {
////                            scope.launch {
////                                if (!it)
////                                    lazyListState.animateScrollToItem(0)
////                            }
//                            onShowAddOn1ContentChange(it)
//                        }
//                    )
//                }
//                AnimatedVisibility(
//                    visible = showAddOn1Content,
//                    enter = slideInVertically() + fadeIn(),
//                    exit = slideOutVertically() + fadeOut()
//                ) {
//                    HorizontalDivider(
//                        modifier = Modifier.padding(top = 5.dp),
//                        thickness = 3.dp,
//                        color = MaterialTheme.colorScheme.outline
//                    )
//                }
//            }
//        }

        // First add on data
//        item (
//            key = 3
//        ) {
//            AnimatedVisibility(
//                visible = showAddOn1Content,
//                enter = slideInVertically() + fadeIn(),
//                exit = slideOutVertically() + fadeOut()
//            ) {
//                TimeConfigurationInput(
//                    timePlayer1HourState = firstAddOnTimePlayer1HourState,
//                    timePlayer1MinuteState = firstAddOnTimePlayer1MinuteState,
//                    timePlayer1SecondState = firstAddOnTimePlayer1SecondState,
//                    timePlayer2HourState = firstAddOnTimePlayer2HourState,
//                    timePlayer2MinuteState = firstAddOnTimePlayer2MinuteState,
//                    timePlayer2SecondState = firstAddOnTimePlayer2SecondState,
//                    incrementPlayer1HourState = firstAddOnIncrementPlayer1HourState,
//                    incrementPlayer1MinuteState = firstAddOnIncrementPlayer1MinuteState,
//                    incrementPlayer1SecondState = firstAddOnIncrementPlayer1SecondState,
//                    incrementPlayer2HourState = firstAddOnIncrementPlayer2HourState,
//                    incrementPlayer2MinuteState = firstAddOnIncrementPlayer2MinuteState,
//                    incrementPlayer2SecondState = firstAddOnIncrementPlayer2SecondState,
//                    movesPlayer1 = firstAddOnMovesPlayer1,
//                    movesPlayer2 = firstAddOnMovesPlayer2,
//                    incrementTypePlayer1 = firstAddOnIncrementTypePlayer1,
//                    incrementTypePlayer2 = firstAddOnIncrementTypePlayer2,
//                    onMovesPlayer1Updated = onFirstAddOnMovesPlayer1Updated,
//                    onMovesPlayer2Updated = onFirstAddOnMovesPlayer2Updated,
//                    onIncrementTypePlayer1Updated = onFirstAddOnIncrementTypePlayer1Updated,
//                    onIncrementTypePlayer2Updated = onFirstAddOnIncrementTypePlayer2Updated,
//                    showPlayer2 = showPlayer2Content
//                )
//            }
//        }

        // Second add on switch
//        item (
//            key = 4
//        ) {
//            AnimatedVisibility(
//                visible = showAddOn1Content,
//                enter = slideInVertically() + fadeIn(),
//                exit = slideOutVertically() + fadeOut()
//            ) {
//                Column (
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(horizontal = 10.dp)
//                ) {
//                    Row(
//                        modifier = Modifier.fillMaxWidth(),
//                        verticalAlignment = Alignment.CenterVertically,
//                        horizontalArrangement = Arrangement.SpaceBetween
//                    ) {
//                        Text("Add on (2):")
//                        Switch(
//                            checked = showAddOn2Content,
//                            onCheckedChange = {
////                                scope.launch {
////                                    if (!it)
////                                        lazyListState.animateScrollToItem(2)
////                                }
//                                onShowAddOn2ContentChange(it)
//                            }
//                        )
//                    }
//                    AnimatedVisibility(
//                        visible = showAddOn2Content,
//                        enter = slideInVertically() + fadeIn(),
//                        exit = slideOutVertically() + fadeOut()
//                    ) {
//                        HorizontalDivider(
//                            modifier = Modifier.padding(top = 5.dp),
//                            thickness = 3.dp,
//                            color = MaterialTheme.colorScheme.outline
//                        )
//                    }
//                }
//            }
//        }

        // Second add on data
//        item (
//            key = 5
//        ) {
//            AnimatedVisibility(
//                visible = showAddOn2Content,
//                enter = slideInVertically() + fadeIn(),
//                exit = slideOutVertically() + fadeOut()
//            ) {
//                TimeConfigurationInput(
//                    timePlayer1HourState = secondAddOnTimePlayer1HourState,
//                    timePlayer1MinuteState = secondAddOnTimePlayer1MinuteState,
//                    timePlayer1SecondState = secondAddOnTimePlayer1SecondState,
//                    timePlayer2HourState = secondAddOnTimePlayer2HourState,
//                    timePlayer2MinuteState = secondAddOnTimePlayer2MinuteState,
//                    timePlayer2SecondState = secondAddOnTimePlayer2SecondState,
//                    incrementPlayer1HourState = secondAddOnIncrementPlayer1HourState,
//                    incrementPlayer1MinuteState = secondAddOnIncrementPlayer1MinuteState,
//                    incrementPlayer1SecondState = secondAddOnIncrementPlayer1SecondState,
//                    incrementPlayer2HourState = secondAddOnIncrementPlayer2HourState,
//                    incrementPlayer2MinuteState = secondAddOnIncrementPlayer2MinuteState,
//                    incrementPlayer2SecondState = secondAddOnIncrementPlayer2SecondState,
//                    movesPlayer1 = secondAddOnMovesPlayer1,
//                    movesPlayer2 = secondAddOnMovesPlayer2,
//                    incrementTypePlayer1 = secondAddOnIncrementTypePlayer1,
//                    incrementTypePlayer2 = secondAddOnIncrementTypePlayer2,
//                    onMovesPlayer1Updated = onSecondAddOnMovesPlayer1Updated,
//                    onMovesPlayer2Updated = onSecondAddOnMovesPlayer2Updated,
//                    onIncrementTypePlayer1Updated = onSecondAddOnIncrementTypePlayer1Updated,
//                    onIncrementTypePlayer2Updated = onSecondAddOnIncrementTypePlayer2Updated,
//                    showPlayer2 = showPlayer2Content
//                )
//            }
//        }
    }
}
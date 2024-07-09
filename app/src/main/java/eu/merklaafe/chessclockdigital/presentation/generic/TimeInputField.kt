package eu.merklaafe.chessclockdigital.presentation.generic

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import eu.merklaafe.chessclockdigital.util.Constants.digitFamily
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun TimeInputField(
    hourState: ScrollWheelState,
    minuteState: ScrollWheelState,
    secondState: ScrollWheelState,
    modifier: Modifier = Modifier,
    showHour: Boolean = true,
    rangeHour: IntRange = (0 .. 9),
    rangeMinute: IntRange = (0 .. 59),
    rangeSecond: IntRange = (0 .. 59),
    textColor: Color = MaterialTheme.colorScheme.primary
) {
    val valuesHour = remember { rangeHour.map { it.toString() } }
    val valuesMinute = remember { rangeMinute.map { String.format("%02d",it) } }
    val valuesSecond = remember { rangeSecond.map { String.format("%02d",it) } }
    var alphaHour = 100f
    var borderHour = BorderStroke(1.dp,textColor)

    if (!showHour) {
        alphaHour = 0f
        borderHour = BorderStroke(0.dp,MaterialTheme.colorScheme.surface)
    }

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        ScrollWheel(
            modifier = Modifier
                .border(borderHour)
                .alpha(alphaHour),
            state = hourState,
            items = valuesHour,
            textModifier = Modifier.padding(5.dp),
            textStyle = LocalTextStyle.current.copy(
                fontFamily = digitFamily
            ),
            textColor = textColor,
            userScrollEnabled = showHour
        )
        Text(
            modifier = Modifier
                .padding(horizontal = 2.dp)
                .alpha(alphaHour),
            text = ":",
            fontFamily = digitFamily,
            color = textColor
        )

        ScrollWheel(
            modifier = Modifier.border(1.dp,textColor),
            state = minuteState,
            items = valuesMinute,
            textModifier = Modifier.padding(5.dp),
            textStyle = LocalTextStyle.current.copy(
                fontFamily = digitFamily
            ),
            textColor = textColor
        )
        Text(
            modifier = Modifier.padding(horizontal = 2.dp),
            text = ":",
            fontFamily = digitFamily,
            color = textColor
        )
        ScrollWheel(
            modifier = Modifier.border(1.dp,textColor),
            state = secondState,
            items = valuesSecond,
            textModifier = Modifier.padding(5.dp),
            textStyle = LocalTextStyle.current.copy(
                fontFamily = digitFamily
            ),
            textColor = textColor
        )
    }
}

// Based on https://stackoverflow.com/questions/68187868/android-jetpack-compose-numberpicker-widget-equivalent

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ScrollWheel(
    items: List<String>,
    state: ScrollWheelState,
    modifier: Modifier = Modifier,
    visibleItemsCount: Int = 3,
    textModifier: Modifier = Modifier,
    textStyle: TextStyle = LocalTextStyle.current,
    userScrollEnabled: Boolean = true,
    textColor: Color = MaterialTheme.colorScheme.primary
) {
    val scope = rememberCoroutineScope()

    val middleItemOffset = visibleItemsCount / 2
    val listScrollCount = Integer.MAX_VALUE
    val listScrollMiddle = listScrollCount / 2
    val listStartIndex = listScrollMiddle - listScrollMiddle % items.size - middleItemOffset + items.indexOf(state.currentItem)

    val listState = rememberLazyListState(initialFirstVisibleItemIndex = listStartIndex)
    val flingBehavior = rememberSnapFlingBehavior(lazyListState = listState)

    val itemHeightPixels = remember { mutableIntStateOf(0) }
    val itemHeightDp = with(LocalDensity.current) {itemHeightPixels.intValue.toDp()}
    val itemWidthPixels = remember { mutableIntStateOf(30)}
    val itemWidthDp = with(LocalDensity.current) {itemWidthPixels.intValue.toDp()}

    val currentItem = remember { mutableStateOf("") }
    val scrollTo = remember { mutableStateOf(false) }

    fun getItem(index: Int) = items[index % items.size]
    fun getScrollIndex(item: String):Int = listScrollMiddle - listScrollMiddle % items.size - middleItemOffset + items.indexOf(item)

    // Update wheel position if state changes from the outside (e.g. from ViewModel or business logic)
    LaunchedEffect(key1 = state.currentItem) {
        scrollTo.value = state.currentItem != currentItem.value
    }

    LaunchedEffect(key1 = scrollTo.value) {
        if (scrollTo.value) {
            listState.scrollToItem(index = getScrollIndex(state.currentItem))
        }
    }

    // Update state when wheel is scrolled
    LaunchedEffect(listState) {
        snapshotFlow { listState.firstVisibleItemIndex }
            .map { index -> getItem(index + middleItemOffset) }
            .distinctUntilChanged()
            .collect {item ->
                state.currentItem = item
                currentItem.value = item
                //Log.d("TimeInputField","State: ${currentItem.value}")
            }
    }

    Box(modifier = modifier) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                modifier = Modifier
                    .width(itemWidthDp)
                    .clickable {
                    if (listState.canScrollForward) {
                        scope.launch {
                            listState.animateScrollToItem(index = listState.firstVisibleItemIndex+1)
                        }
                    }
                },
                imageVector = Icons.Default.KeyboardArrowUp,
                contentDescription = "Arrow Up",
                tint = textColor
            )
            LazyColumn(
                state = listState,
                flingBehavior = flingBehavior,
                horizontalAlignment = Alignment.CenterHorizontally,
                userScrollEnabled = userScrollEnabled,
                modifier = Modifier
                    .height(itemHeightDp * visibleItemsCount)
                    .border(width = 1.dp, color = textColor)
                    .graphicsLayer(compositingStrategy = CompositingStrategy.Offscreen)
                    .drawWithContent {
                        drawContent()
                        drawRect(
                            brush = Brush.verticalGradient(
                                0f to Color.Transparent,
                                0.3f to Color.Black,
                                0.7f to Color.Black,
                                1f to Color.Transparent
                            ),
                            blendMode = BlendMode.DstIn
                        )
                    }
                    .onGloballyPositioned {
                        itemWidthPixels.intValue = it.size.width
                    }
            ) {
                items(listScrollCount) { index ->
                    Text(
                        text = getItem(index),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = textStyle,
                        color = textColor,
                        modifier = Modifier
                            .onSizeChanged { size -> itemHeightPixels.intValue = size.height }
                            .then(textModifier)
                    )
                }
            }
            Icon(
                modifier = Modifier
                    .width(itemWidthDp)
                    .clickable {
                    if (listState.canScrollBackward) {
                        scope.launch {
                            listState.animateScrollToItem(index = listState.firstVisibleItemIndex-1)
                        }
                    }
                },
                imageVector = Icons.Default.KeyboardArrowDown,
                contentDescription = "Arrow Down",
                tint = textColor
            )
        }
    }
}

class ScrollWheelState(initialValue: String = "") {
    var currentItem by mutableStateOf(initialValue)

    fun update(newValue: String) {
        currentItem = newValue
    }
}


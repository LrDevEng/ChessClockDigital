package eu.merklaafe.chessclockdigital.presentation.screens.timeselection

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import eu.merklaafe.chessclockdigital.data_source.database.entity.PresetGame
import eu.merklaafe.chessclockdigital.model.GameCategory
import eu.merklaafe.chessclockdigital.util.Constants.BLITZ_GAME_LIMIT_IN_MILLIS
import eu.merklaafe.chessclockdigital.util.Constants.BULLET_GAME_LIMIT_IN_MILLIS
import eu.merklaafe.chessclockdigital.util.Constants.RAPID_GAME_LIMIT_IN_MILLIS
import eu.merklaafe.chessclockdigital.R
import eu.merklaafe.chessclockdigital.model.IncrementType
import eu.merklaafe.chessclockdigital.util.Constants.digitFamily
import eu.merklaafe.chessclockdigital.util.parseMillisToDigits

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TimeSelectionContent(
    paddingValues: PaddingValues,
    presetGames: List<PresetGame>,
    showDeleteOption: Boolean,
    floatingActionButtonHeight: Dp = 0.dp,
    nestedScrollConnection: NestedScrollConnection,
    lazyColumnState: LazyListState,
    selectedRadioButtonId: Int,
    onRadioButtonClick: (Int) -> Unit,
    onDeleteItemClick: (Int) -> Unit,
) {
    val positionList = ArrayList<Int?>()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
            .navigationBarsPadding()
            .padding(
                top = paddingValues.calculateTopPadding(),
                bottom = paddingValues.calculateBottomPadding()
            )
    ) {
        LazyColumn(
            modifier = Modifier
                .weight(1F)
                .fillMaxWidth()
                .nestedScroll(nestedScrollConnection),
            state = lazyColumnState
        ) {
            GameCategory.entries.forEach { gameCategory ->
                if (presetGames.any { calculateGameCategory(it) == gameCategory }) {
                    stickyHeader(contentType = gameCategory) {
                        Box(
                            modifier = Modifier
                                .padding(bottom = 10.dp)
                        ) {
                            var symbol = ""
                            when (gameCategory) {
                                GameCategory.Bullet -> {}
                                GameCategory.Blitz -> {}
                                GameCategory.Rapid -> {}
                                GameCategory.Classic -> {}
                                GameCategory.Special -> {}
                            }

                            Surface(
                                color = MaterialTheme.colorScheme.primary,
                                contentColor = MaterialTheme.colorScheme.onPrimary,
                                shadowElevation = 4.dp
                            ) {
                                Row(
                                    modifier = Modifier
                                        .padding(horizontal = 10.dp)
                                        .fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        modifier = Modifier
                                            .weight(5F)
                                            .fillMaxHeight(),
                                        text = "$symbol $gameCategory",
                                        fontWeight = FontWeight.SemiBold,
                                        style = MaterialTheme.typography.headlineSmall,
                                    )
                                    IconButton(
                                        modifier = Modifier
                                            .weight(1F)
                                            .fillMaxHeight(),
                                        onClick = { /*toDo*/ }
                                    ) {
                                        Icon(
                                            modifier = Modifier.fillMaxSize(),
                                            imageVector = Icons.Default.Info,
                                            contentDescription = "Info"
                                        )
                                    }
                                }
                            }
                        }
                    }
                    // Add null to position list for each sticky header
                    positionList.add(null)

                    presetGames.forEach { presetGame ->
                        val category = calculateGameCategory(presetGame)
                        if (category == gameCategory) {
                            item(
                                key = presetGame.id,
                                content = {
//                                    PresetGameListItem(
//                                        selected = (selectedRadioButtonId == presetGame.id),
//                                        showDeleteOption = showDeleteOption,
//                                        showPlayer2Info = (gameCategory == GameCategory.Special),
//                                        standardInfoPlayer1 = presetGame.toText().standardInfoPlayer1,
//                                        firstAddOnInfoPlayer1 = presetGame.toText().firstAddOnInfoPlayer1,
//                                        secondAddOnInfoPlayer1 = presetGame.toText().secondAddOnInfoPlayer1,
//                                        standardInfoPlayer2 = presetGame.toText().standardInfoPlayer2,
//                                        firstAddOnInfoPlayer2 = presetGame.toText().firstAddOnInfoPlayer2,
//                                        secondAddOnInfoPlayer2 = presetGame.toText().secondAddOnInfoPlayer2,
//                                        onRadioButtonClick = { onRadioButtonClick(presetGame.id) },
//                                        onDeleteClick = { onDeleteItemClick(presetGame.id) }
//                                    )
                                    PresetGameListItem2(
                                        selected = (selectedRadioButtonId == presetGame.id),
                                        showDeleteOption = showDeleteOption,
                                        showPlayer2Info = (gameCategory == GameCategory.Special),
                                        standardTimePlayer1 = parseMillisToDigits(presetGame.standardTimePlayer1, clipLeadingZeros = true),
                                        standardIncrementTypePlayer1 = presetGame.standardIncrementTypePlayer1.name,
                                        standardIncrementPlayer1 = parseMillisToDigits(presetGame.standardIncrementPlayer1, clipLeadingZeros = true),
                                        firstAddOnMovePlayer1 = presetGame.firstAddOnMovePlayer1?.toString(),
                                        firstAddOnTimePlayer1 = parseMillisToDigits(presetGame.firstAddOnTimePlayer1, clipLeadingZeros = true),
                                        firstAddOnIncrementTypePlayer1 = presetGame.firstAddOnIncrementTypePlayer1.name,
                                        firstAddOnIncrementPlayer1 = parseMillisToDigits(presetGame.firstAddOnIncrementPlayer1, clipLeadingZeros = true),
                                        secondAddOnMovePlayer1 = presetGame.secondAddOnMovePlayer1?.toString(),
                                        secondAddOnTimePlayer1 = parseMillisToDigits(presetGame.secondAddOnTimePlayer1, clipLeadingZeros = true),
                                        secondAddOnIncrementTypePlayer1 = presetGame.secondAddOnIncrementTypePlayer1.name,
                                        secondAddOnIncrementPlayer1 = parseMillisToDigits(presetGame.secondAddOnIncrementPlayer1, clipLeadingZeros = true),
                                        standardTimePlayer2 = parseMillisToDigits(presetGame.standardTimePlayer2, clipLeadingZeros = true),
                                        standardIncrementTypePlayer2 = presetGame.standardIncrementTypePlayer2.name,
                                        standardIncrementPlayer2 = parseMillisToDigits(presetGame.standardIncrementPlayer2, clipLeadingZeros = true),
                                        firstAddOnMovePlayer2 = presetGame.firstAddOnMovePlayer2?.toString(),
                                        firstAddOnTimePlayer2 = parseMillisToDigits(presetGame.firstAddOnTimePlayer2, clipLeadingZeros = true),
                                        firstAddOnIncrementTypePlayer2 = presetGame.firstAddOnIncrementTypePlayer2.name,
                                        firstAddOnIncrementPlayer2 = parseMillisToDigits(presetGame.firstAddOnIncrementPlayer2, clipLeadingZeros = true),
                                        secondAddOnMovePlayer2 = presetGame.secondAddOnMovePlayer2?.toString(),
                                        secondAddOnTimePlayer2 = parseMillisToDigits(presetGame.secondAddOnTimePlayer2, clipLeadingZeros = true),
                                        secondAddOnIncrementTypePlayer2 = presetGame.secondAddOnIncrementTypePlayer2.name,
                                        secondAddOnIncrementPlayer2 = parseMillisToDigits(presetGame.secondAddOnIncrementPlayer2, clipLeadingZeros = true),
                                        onRadioButtonClick = { onRadioButtonClick(presetGame.id) },
                                        onDeleteClick = { onDeleteItemClick(presetGame.id) }
                                    )
                                }
                            )
                            // Add preset game id to position list for each saved game
                            positionList.add(presetGame.id)
                        }
                    }
                }
            }

            item {
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(floatingActionButtonHeight + 10.dp)
                )
            }
        }
    }
    LaunchedEffect(key1 = presetGames) {
        if (!showDeleteOption) {
            var index = positionList.indexOf(selectedRadioButtonId) - 2
            if (index < 0) {
                index = 0
            } else if (index > lazyColumnState.layoutInfo.totalItemsCount - 1) {
                index = lazyColumnState.layoutInfo.totalItemsCount - 1
            }

            lazyColumnState.scrollToItem(
                index = index,
                scrollOffset = 0
            )
        }
    }
}

@Composable
fun PresetGameListItem(
    selected: Boolean,
    showDeleteOption: Boolean,
    showPlayer2Info: Boolean,
    standardInfoPlayer1: String,
    firstAddOnInfoPlayer1: String,
    secondAddOnInfoPlayer1: String,
    standardInfoPlayer2: String,
    firstAddOnInfoPlayer2: String,
    secondAddOnInfoPlayer2: String,
    shape: Shape = RoundedCornerShape(
        topStart = 20F,
        topEnd = 20F,
        bottomStart = 20F,
        bottomEnd = 20F
    ),
    borderStroke: BorderStroke = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
    textStyleLarge: TextStyle = MaterialTheme.typography.titleLarge,
    textStyle: TextStyle = MaterialTheme.typography.bodySmall,
    onRadioButtonClick: () -> Unit,
    onDeleteClick: () -> Unit,
) {
    var openAlertDialog by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .padding(horizontal = 10.dp)
            .fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(
            modifier = Modifier
                .padding(bottom = 10.dp)
                .weight(5F),
            shape = shape,
            color = MaterialTheme.colorScheme.onPrimary,
            contentColor = MaterialTheme.colorScheme.primary,
            border = borderStroke,
            shadowElevation = 4.dp
        ) {
            Column(
                modifier = Modifier
                    .padding(vertical = 5.dp, horizontal = 10.dp)
                    .fillMaxWidth(),
            ) {
//                Text(
//                    text = standardInfoPlayer1,
//                    maxLines = 1,
//                    overflow = TextOverflow.Ellipsis,
//                    fontSize = 16.sp,
//                    fontFamily = digitFamily
//                )
                MyText(
                    modifier = Modifier.padding(vertical = 2.dp),
                    text = standardInfoPlayer1
                )
                if (firstAddOnInfoPlayer1.isNotEmpty()) {
//                    Text(
//                        text = firstAddOnInfoPlayer1,
//                        maxLines = 1,
//                        overflow = TextOverflow.Ellipsis,
//                        style = textStyle,
//                        fontFamily = digitFamily
//                    )
                    HorizontalDivider(thickness = 1.dp)
                    MyText(
                        modifier = Modifier.padding(bottom = 2.dp),
                        text = firstAddOnInfoPlayer1,
                        fontSizeDigits = 8.sp,
                        fontSizeWhitespace = 4.sp,
                        fontSizeRegular = 12.sp
                    )
                }
                if (secondAddOnInfoPlayer1.isNotEmpty()) {
//                    Text(
//                        text = secondAddOnInfoPlayer1,
//                        maxLines = 1,
//                        overflow = TextOverflow.Ellipsis,
//                        style = textStyle,
//                        fontFamily = digitFamily
//                    )
                    HorizontalDivider(thickness = 1.dp)
                    MyText(
                        modifier = Modifier.padding(bottom = 2.dp),
                        text = secondAddOnInfoPlayer1,
                        fontSizeDigits = 8.sp,
                        fontSizeWhitespace = 4.sp,
                        fontSizeRegular = 12.sp
                    )
                }
                if (showPlayer2Info) {
                    HorizontalDivider(
                        modifier = Modifier.padding(bottom = 2.dp),
                        thickness = 3.dp
                    )
//                    Text(
//                        text = standardInfoPlayer2,
//                        maxLines = 1,
//                        overflow = TextOverflow.Ellipsis,
//                        style = textStyleLarge,
//                        fontFamily = digitFamily
//                    )
                    MyText(
                        modifier = Modifier.padding(vertical = 2.dp),
                        text = standardInfoPlayer2
                    )
                    if (firstAddOnInfoPlayer2.isNotEmpty()) {
//                        Text(
//                            text = firstAddOnInfoPlayer2,
//                            maxLines = 1,
//                            overflow = TextOverflow.Ellipsis,
//                            style = textStyle,
//                            fontFamily = digitFamily
//                        )
                        HorizontalDivider(thickness = 1.dp)
                        MyText(
                            modifier = Modifier.padding(bottom = 2.dp),
                            text = firstAddOnInfoPlayer2,
                            fontSizeDigits = 8.sp,
                            fontSizeWhitespace = 4.sp,
                            fontSizeRegular = 12.sp
                        )
                    }
                    if (secondAddOnInfoPlayer2.isNotEmpty()) {
//                        Text(
//                            text = secondAddOnInfoPlayer2,
//                            maxLines = 1,
//                            overflow = TextOverflow.Ellipsis,
//                            style = textStyle,
//                            fontFamily = digitFamily
//                        )
                        HorizontalDivider(thickness = 1.dp)
                        MyText(
                            modifier = Modifier.padding(bottom = 2.dp),
                            text = secondAddOnInfoPlayer2,
                            fontSizeDigits = 8.sp,
                            fontSizeWhitespace = 4.sp,
                            fontSizeRegular = 12.sp
                        )
                    }
                }
            }
        }

        if (showDeleteOption && !selected) {
            IconButton(
                modifier = Modifier
                    .padding(bottom = 10.dp)
                    .weight(1F),
                onClick = {
                    openAlertDialog = true
                }
            ) {
                Icon(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(id = R.drawable.baseline_delete_forever_24),
                    contentDescription = "Delete"
                )
            }
        } else {
            RadioButton(
                modifier = Modifier
                    .padding(bottom = 10.dp)
                    .weight(1F),
                selected = selected,
                onClick = onRadioButtonClick
            )
        }

        if (openAlertDialog) {
            AlertDialog(
                text = {
                    Text(text = "Delete this time control setting?")
                },
                onDismissRequest = { openAlertDialog = false },
                dismissButton = {
                    Button(onClick = { openAlertDialog = false }) {
                        Text(text = "Cancel")
                    }
                },
                confirmButton = {
                    Button(onClick = {
                        onDeleteClick()
                        openAlertDialog = false
                    }) {
                        Text(text = "Delete")
                    }
                }
            )
        }
    }
}


@Composable
fun PresetGameListItem2(
    selected: Boolean,
    showDeleteOption: Boolean,
    showPlayer2Info: Boolean,

    standardTimePlayer1: String,
    standardIncrementTypePlayer1: String,
    standardIncrementPlayer1: String,
    firstAddOnMovePlayer1: String?,
    firstAddOnTimePlayer1: String,
    firstAddOnIncrementTypePlayer1: String,
    firstAddOnIncrementPlayer1: String,
    secondAddOnMovePlayer1: String?,
    secondAddOnTimePlayer1: String,
    secondAddOnIncrementTypePlayer1: String,
    secondAddOnIncrementPlayer1: String,

    standardTimePlayer2: String,
    standardIncrementTypePlayer2: String,
    standardIncrementPlayer2: String,
    firstAddOnMovePlayer2: String?,
    firstAddOnTimePlayer2: String,
    firstAddOnIncrementTypePlayer2: String,
    firstAddOnIncrementPlayer2: String,
    secondAddOnMovePlayer2: String?,
    secondAddOnTimePlayer2: String,
    secondAddOnIncrementTypePlayer2: String,
    secondAddOnIncrementPlayer2: String,

    shape: Shape = RoundedCornerShape(
        topStart = 20F,
        topEnd = 20F,
        bottomStart = 20F,
        bottomEnd = 20F
    ),
    borderStroke: BorderStroke = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
    textStyleLarge: TextStyle = MaterialTheme.typography.titleLarge,
    textStyle: TextStyle = MaterialTheme.typography.bodySmall,
    onRadioButtonClick: () -> Unit,
    onDeleteClick: () -> Unit,
) {
    var openAlertDialog by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .padding(horizontal = 10.dp)
            .fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(
            modifier = Modifier
                .padding(bottom = 10.dp)
                .weight(5F),
            shape = shape,
            color = MaterialTheme.colorScheme.onPrimary,
            contentColor = MaterialTheme.colorScheme.primary,
            border = borderStroke,
            shadowElevation = 4.dp
        ) {
            Column(
                modifier = Modifier
                    .padding(vertical = 5.dp, horizontal = 10.dp)
                    .fillMaxWidth(),
            ) {
                // Player 1 info
                MyText2(
                    time = standardTimePlayer1,
                    move = "",
                    incrementType = standardIncrementTypePlayer1,
                    increment = standardIncrementPlayer1
                )
                if (firstAddOnMovePlayer1 != null) {
                    HorizontalDivider(
                        modifier = Modifier.padding(top = 4.dp),
                        thickness = 1.dp,
                        color = MaterialTheme.colorScheme.outline
                    )
                    MyText2(
                        time = firstAddOnTimePlayer1,
                        move = firstAddOnMovePlayer1,
                        incrementType = firstAddOnIncrementTypePlayer1,
                        increment = firstAddOnIncrementPlayer1,
                        addOn = true,
                        fontSizeDigits = 8.sp,
                        fontSizeRegular = 12.sp,
                        fontSizeWhitespace = 4.sp
                    )
                    if (secondAddOnMovePlayer1 != null) {
                        HorizontalDivider(
                            thickness = 1.dp,
                            color = MaterialTheme.colorScheme.outline
                        )
                        MyText2(
                            time = secondAddOnTimePlayer1,
                            move = (secondAddOnMovePlayer1.toInt() + firstAddOnMovePlayer1.toInt()).toString(),
                            incrementType = secondAddOnIncrementTypePlayer1,
                            increment = secondAddOnIncrementPlayer1,
                            addOn = true,
                            fontSizeDigits = 8.sp,
                            fontSizeRegular = 12.sp,
                            fontSizeWhitespace = 4.sp
                        )
                    }
                }

                // Player 2 info
                if (showPlayer2Info) {
                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 4.dp),
                        thickness = 3.dp,
                        color = MaterialTheme.colorScheme.outline
                    )
                    MyText2(
                        time = standardTimePlayer2,
                        move = "",
                        incrementType = standardIncrementTypePlayer2,
                        increment = standardIncrementPlayer2
                    )
                    if (firstAddOnMovePlayer2 != null) {
                        HorizontalDivider(
                            modifier = Modifier.padding(top = 4.dp),
                            thickness = 1.dp,
                            color = MaterialTheme.colorScheme.outline
                        )
                        MyText2(
                            time = firstAddOnTimePlayer2,
                            move = firstAddOnMovePlayer2,
                            incrementType = firstAddOnIncrementTypePlayer2,
                            increment = firstAddOnIncrementPlayer2,
                            addOn = true,
                            fontSizeDigits = 8.sp,
                            fontSizeRegular = 12.sp,
                            fontSizeWhitespace = 4.sp
                        )
                        if (secondAddOnMovePlayer2 != null) {
                            HorizontalDivider(
                                thickness = 1.dp,
                                color = MaterialTheme.colorScheme.outline
                            )
                            MyText2(
                                time = secondAddOnTimePlayer2,
                                move = (secondAddOnMovePlayer2.toInt() + secondAddOnMovePlayer2.toInt()).toString(),
                                incrementType = secondAddOnIncrementTypePlayer2,
                                increment = secondAddOnIncrementPlayer2,
                                addOn = true,
                                fontSizeDigits = 8.sp,
                                fontSizeRegular = 12.sp,
                                fontSizeWhitespace = 4.sp
                            )
                        }
                    }
                }
            }
        }

        if (showDeleteOption && !selected) {
            IconButton(
                modifier = Modifier
                    .padding(bottom = 10.dp)
                    .weight(1F),
                onClick = {
                    openAlertDialog = true
                }
            ) {
                Icon(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(id = R.drawable.baseline_delete_forever_24),
                    contentDescription = "Delete",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        } else {
            RadioButton(
                modifier = Modifier
                    .padding(bottom = 10.dp)
                    .weight(1F),
                selected = selected,
                onClick = onRadioButtonClick
            )
        }

        if (openAlertDialog) {
            AlertDialog(
                text = {
                    Text(text = "Delete this time control setting?")
                },
                onDismissRequest = { openAlertDialog = false },
                dismissButton = {
                    Button(onClick = { openAlertDialog = false }) {
                        Text(text = "Cancel")
                    }
                },
                confirmButton = {
                    Button(onClick = {
                        onDeleteClick()
                        openAlertDialog = false
                    }) {
                        Text(text = "Delete")
                    }
                }
            )
        }
    }
}

@Composable
fun MyText2 (
    modifier: Modifier = Modifier,
    time: String,
    move: String,
    incrementType: String,
    increment: String,
    addOn: Boolean = false,
    fontSizeDigits: TextUnit = 14.sp,
    fontSizeRegular: TextUnit = 18.sp,
    fontSizeWhitespace: TextUnit = 6.sp,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (addOn) {
            Text(
                text = "+",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontSize = fontSizeRegular
            )
        }
        Text(
            text = time,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontSize = fontSizeDigits,
            fontFamily = digitFamily
        )
        if (incrementType != IncrementType.None.name) {
            Text(
                text = " ",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontSize = fontSizeWhitespace
            )
            Text(
                text = "↑",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontSize = fontSizeRegular
            )
            Text(
                text = " ",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontSize = fontSizeWhitespace
            )
            Text(
                text = increment,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontSize = fontSizeDigits,
                fontFamily = digitFamily
            )
            Text(
                text = " ",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontSize = fontSizeWhitespace
            )
            Text(
                text = incrementType,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontSize = fontSizeRegular
            )
        }
        if (move.isNotEmpty()) {
            Text(
                text = " ",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontSize = fontSizeWhitespace
            )
            Text(
                text = "@",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontSize = fontSizeRegular
            )
            Text(
                text = move,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontSize = fontSizeDigits,
                fontFamily = digitFamily
            )
            Text(
                text = " ",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontSize = fontSizeWhitespace
            )
            Text(
                text = "Moves",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontSize = fontSizeRegular
            )
        }
    }
}

@Composable
fun MyText (
    text: String,
    fontSizeDigits: TextUnit = 14.sp,
    fontSizeWhitespace: TextUnit = 6.sp,
    fontSizeRegular: TextUnit = 18.sp,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        text.forEach { 
            if (it.isDigit() || it == ':') {
                Text(
                    text = it.toString(),
                    fontSize = fontSizeDigits,
                    fontFamily = digitFamily,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            } else if (it.isWhitespace()) {
                Text(
                    text = it.toString(),
                    fontSize = fontSizeWhitespace,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            } else {
                Text(
                    text = it.toString(),
                    fontSize = fontSizeRegular,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

private fun calculateGameCategory(presetGame: PresetGame): GameCategory {
    val cumulatedTime = presetGame.standardTimePlayer1 + presetGame.firstAddOnTimePlayer1 + presetGame.secondAddOnTimePlayer1
    var gameCategory = GameCategory.Classic

    if (presetGame.deviatingSettings()) {
        gameCategory = GameCategory.Special
    } else {
        if (cumulatedTime < BULLET_GAME_LIMIT_IN_MILLIS) {
            gameCategory = GameCategory.Bullet
        } else if (cumulatedTime < BLITZ_GAME_LIMIT_IN_MILLIS) {
            gameCategory = GameCategory.Blitz
        } else if (cumulatedTime < RAPID_GAME_LIMIT_IN_MILLIS) {
            gameCategory = GameCategory.Rapid
        }
    }
    return gameCategory
}
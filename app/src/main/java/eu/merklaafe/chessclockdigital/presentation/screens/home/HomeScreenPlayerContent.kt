package eu.merklaafe.chessclockdigital.presentation.screens.home

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import eu.merklaafe.chessclockdigital.model.GameState
import eu.merklaafe.chessclockdigital.presentation.generic.AutoSizeSingleLineText
import eu.merklaafe.chessclockdigital.presentation.generic.Blinker
import eu.merklaafe.chessclockdigital.presentation.generic.DpToSp
import eu.merklaafe.chessclockdigital.util.Constants.digitFamily

@Composable
fun HomeScreenPlayerContent(
    modifier: Modifier = Modifier,
    timerStatePrimary: String,
    moveNumber: Int,
    gameState: GameState,
    timerStateSecondary: String = "",
    enabled: Boolean = true,
    lostGame: Boolean = false,
    preferenceShowOpponentTimePortrait: Boolean = true,
    preferenceShowOpponentTimeLandscape: Boolean = false,
    preferenceShowGameInfoPortrait: Boolean = true,
    preferenceShowGameInfoLandscape: Boolean = true,
    borderStroke: BorderStroke = BorderStroke(5.dp, Brush.linearGradient(listOf(MaterialTheme.colorScheme.secondary,MaterialTheme.colorScheme.secondary))),
    textColor: Color = MaterialTheme.colorScheme.primary,
    backgroundColor: Color = MaterialTheme.colorScheme.onPrimary
) {
    val orientation = LocalConfiguration.current.orientation
    var preferenceShowOpponentTime = preferenceShowOpponentTimePortrait
    var preferenceShowGameInfo = preferenceShowGameInfoPortrait

    if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
        preferenceShowOpponentTime = preferenceShowOpponentTimeLandscape
        preferenceShowGameInfo = preferenceShowGameInfoLandscape
    }

    val blinkerTimeOn = 1500L
    val blinkerTimeOff = 500L
    var dynamicTextColor = textColor

    if (gameState == GameState.Finished && lostGame) {
        dynamicTextColor = MaterialTheme.colorScheme.error.copy(
            alpha = textColor.alpha
        )
    }

//    LaunchedEffect(key1 = orientation) {
//        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
//            preferenceShowOpponentTime = preferenceShowOpponentTimeLandscape
//            preferenceShowGameInfo = preferenceShowGameInfoLandscape
//        } else {
//            preferenceShowOpponentTime = preferenceShowOpponentTimePortrait
//            preferenceShowGameInfo = preferenceShowGameInfoPortrait
//        }
//    }

    Card(
        modifier = modifier
            .fillMaxSize()
            .padding(10.dp),
        border = borderStroke,
    ) {
        Box (
            modifier = Modifier
                .fillMaxSize()
                .background(borderStroke.brush)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(backgroundColor),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(modifier = Modifier
                            .weight(1F, fill = true)
                            .fillMaxSize(),
                            contentAlignment = Alignment.BottomEnd
                        ) {
                            if (timerStateSecondary.isNotEmpty()) {
                                Row(
                                    modifier = Modifier
                                        .padding(
                                        top = 5.dp,
                                        start = 20.dp,
                                        end = 20.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    when(gameState) {
                                        is GameState.Idle -> {
                                            if (preferenceShowGameInfo) {
                                                var fixedSize: Pair<TextUnit?, TextUnit?> by remember {
                                                    mutableStateOf(
                                                        null to null
                                                    )
                                                }
                                                Blinker(
                                                    content = {
                                                        AutoSizeSingleLineText(
                                                            modifier = Modifier,
                                                            text = "TAP TO START <--",
                                                            maxFontSize = DpToSp(dp = 20.dp),
                                                            minFontSize = DpToSp(dp = 5.dp),
                                                            fixedSize = fixedSize,
                                                            color = dynamicTextColor,
                                                            calculatedFontSize = {
                                                                fixedSize = it
                                                            }
                                                        )
                                                    },
                                                    timeOn = blinkerTimeOn,
                                                    timeOff = blinkerTimeOff
                                                )
                                            }
                                        }
                                        is GameState.Running -> {
                                            if (preferenceShowOpponentTime) {
                                                AutoSizeSingleLineText(
                                                    modifier = Modifier,
                                                    text = "OPPONENT: ",
                                                    maxFontSize = DpToSp(dp = 20.dp),
                                                    minFontSize = DpToSp(dp = 5.dp),
                                                    color = dynamicTextColor
                                                )

                                                AutoSizeSingleLineText(
                                                    modifier = Modifier
                                                        .padding(
                                                            start = 10.dp,
                                                            end = 5.dp,
                                                        ),
                                                    text = timerStateSecondary,
                                                    maxFontSize = DpToSp(dp = 20.dp),
                                                    minFontSize = DpToSp(dp = 5.dp),
                                                    fontFamily = digitFamily,
                                                    letterSpacing = 2.sp,
                                                    color = dynamicTextColor
                                                )
                                            }
                                        }
                                        is GameState.Paused -> {
                                            if (preferenceShowGameInfo) {
                                                if (enabled) {
                                                    var fixedSize: Pair<TextUnit?, TextUnit?> by remember {
                                                        mutableStateOf(
                                                            null to null
                                                        )
                                                    }
                                                    Blinker(
                                                        content = {
                                                            Column {
                                                                AutoSizeSingleLineText(
                                                                    modifier = Modifier,
                                                                    text = "TAP TO CONTINUE <--",
                                                                    maxFontSize = DpToSp(dp = 20.dp),
                                                                    minFontSize = DpToSp(dp = 5.dp),
                                                                    fixedSize = fixedSize,
                                                                    color = dynamicTextColor,
                                                                    calculatedFontSize = {
                                                                        fixedSize = it
                                                                    }
                                                                )
                                                            }
                                                        },
                                                        timeOn = blinkerTimeOn,
                                                        timeOff = blinkerTimeOff
                                                    )
                                                } else {
                                                    var fixedSize: Pair<TextUnit?, TextUnit?> by remember {
                                                        mutableStateOf(
                                                            null to null
                                                        )
                                                    }
                                                    Blinker(
                                                        content = {
                                                            AutoSizeSingleLineText(
                                                                modifier = Modifier,
                                                                text = "GAME PAUSED <--",
                                                                maxFontSize = DpToSp(dp = 20.dp),
                                                                minFontSize = DpToSp(dp = 5.dp),
                                                                fixedSize = fixedSize,
                                                                color = dynamicTextColor,
                                                                calculatedFontSize = {
                                                                    fixedSize = it
                                                                }
                                                            )
                                                        },
                                                        timeOn = blinkerTimeOn,
                                                        timeOff = blinkerTimeOff
                                                    )
                                                }
                                            }
                                        }
                                        is GameState.Finished -> {
                                            if (preferenceShowGameInfo) {
                                                if (lostGame) {
                                                    var fixedSize: Pair<TextUnit?, TextUnit?> by remember {
                                                        mutableStateOf(
                                                            null to null
                                                        )
                                                    }
                                                    Blinker(
                                                        content = {
                                                            AutoSizeSingleLineText(
                                                                modifier = Modifier,
                                                                text = "LOST ON TIME <--",
                                                                maxFontSize = DpToSp(dp = 20.dp),
                                                                minFontSize = DpToSp(dp = 5.dp),
                                                                fixedSize = fixedSize,
                                                                color = dynamicTextColor,
                                                                calculatedFontSize = {
                                                                    fixedSize = it
                                                                }
                                                            )
                                                        },
                                                        timeOn = blinkerTimeOn,
                                                        timeOff = blinkerTimeOff
                                                    )
                                                } else {
                                                    var fixedSize: Pair<TextUnit?, TextUnit?> by remember {
                                                        mutableStateOf(
                                                            null to null
                                                        )
                                                    }
                                                    Blinker(
                                                        content = {
                                                            AutoSizeSingleLineText(
                                                                modifier = Modifier,
                                                                text = "WON ON TIME <--",
                                                                maxFontSize = DpToSp(dp = 20.dp),
                                                                minFontSize = DpToSp(dp = 5.dp),
                                                                fixedSize = fixedSize,
                                                                color = dynamicTextColor,
                                                                calculatedFontSize = {
                                                                    fixedSize = it
                                                                }
                                                            )
                                                        },
                                                        timeOn = blinkerTimeOn,
                                                        timeOff = blinkerTimeOff
                                                    )
                                                }
                                            }
                                        }
                                    }                                    
                                }
                            }
                        }
                        Box(
                            modifier = Modifier
                                .weight(3F, fill = true)
                                .padding(
                                    top = 2.dp,
                                    bottom = 2.dp
                                )
                                .fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                HorizontalDivider(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(1.dp),
                                    color = textColor
                                )
                                AutoSizeSingleLineText(
                                    modifier = Modifier
                                        .padding(
                                            start = 10.dp,
                                            end = 5.dp,
                                            top = 10.dp,
                                            bottom = 10.dp
                                        ),
                                    text = timerStatePrimary,
                                    fontFamily = digitFamily,
                                    letterSpacing = DpToSp(dp = 5.dp),
                                    color = dynamicTextColor
                                )
                                HorizontalDivider(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(1.dp),
                                    color = textColor
                                )
                            }
                        }
                        Box(modifier = Modifier
                            .padding(
                                start = 20.dp,
                                end = 20.dp,
                                bottom = 5.dp
                            )
                            .weight(1F, fill = true)
                            .fillMaxSize(),
                            contentAlignment = Alignment.TopStart
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                AutoSizeSingleLineText(
                                    modifier = Modifier,
                                    text = "MOVES: ",
                                    maxFontSize = DpToSp(dp = 20.dp),
                                    minFontSize = DpToSp(dp = 5.dp),
                                    color = dynamicTextColor
                                )

                                AutoSizeSingleLineText(
                                    modifier = Modifier
                                        .padding(
                                            start = 10.dp,
                                            end = 5.dp,
                                        ),
                                    text = moveNumber.toString(),
                                    maxFontSize = DpToSp(dp = 20.dp),
                                    minFontSize = DpToSp(dp = 5.dp),
                                    fontFamily = digitFamily,
                                    letterSpacing = 2.sp,
                                    color = dynamicTextColor
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun HomeScreenPlayerContentPreview(
    
) {
    HomeScreenPlayerContent(
        timerStatePrimary = "5:43:21",
        moveNumber = 0,
        gameState = GameState.Running
    )
}
package eu.merklaafe.chessclockdigital.presentation.screens.home

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import android.media.AudioManager
import android.media.MediaPlayer
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import eu.merklaafe.chessclockdigital.R
import eu.merklaafe.chessclockdigital.model.GameState
import eu.merklaafe.chessclockdigital.model.Player
import eu.merklaafe.chessclockdigital.presentation.generic.ButtonLayout

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    timer1State: String,
    timer2State: String,
    movesPlayer1: Int,
    movesPlayer2: Int,
    gameState: GameState,
    playersTurn: Player,
    backgroundColor: Color = Color.Black,
    preferenceSound: Boolean = true,
    preferenceShowOpponentTimePortrait: Boolean = true,
    preferenceShowOpponentTimeLandscape: Boolean = false,
    preferenceShowGameInfoPortrait: Boolean = true,
    preferenceShowGameInfoLandscape: Boolean = true,
    onPlayer1Click: () -> Unit,
    onPlayer2Click: () -> Unit,
    onPlayPauseClick: () -> Unit,
    onRefreshClick: () -> Unit,
    onEditClick: () -> Unit,
    onSettingsClick: () -> Unit
) {
    val orientation = LocalConfiguration.current.orientation
    val player1Enabled = (gameState == GameState.Idle) || (gameState != GameState.Finished && playersTurn == Player.Player1)
    val player2Enabled = (gameState == GameState.Idle) || (gameState != GameState.Finished && playersTurn == Player.Player2)
    val showPause = gameState == GameState.Running
    val resetEnabled = gameState != GameState.Idle

    var openResetDialog by remember{ mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) {
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                // Player 1 ui
                PlayerUi(
                    modifier = Modifier
                        .weight(4.5F, fill = true)
                        .background(backgroundColor),
                    enabled = player1Enabled,
                    preferenceSound = preferenceSound,
                    preferenceShowGameInfoLandscape = preferenceShowGameInfoLandscape,
                    preferenceShowGameInfoPortrait = preferenceShowGameInfoPortrait,
                    preferenceShowOpponentTimeLandscape = preferenceShowOpponentTimeLandscape,
                    preferenceShowOpponentTimePortrait = preferenceShowOpponentTimePortrait,
                    lostGame = (gameState == GameState.Finished && playersTurn == Player.Player1),
                    timerStatePrimary = timer1State,
                    moveNumber = movesPlayer1,
                    timerStateSecondary = timer2State,
                    gameState = gameState,
                    onClick = onPlayer1Click
                )

                // General ui (action buttons)
                GeneralUi(
                    modifier = Modifier
                        .weight(1F, fill = true)
                        .background(backgroundColor),
                    showPause = showPause,
                    resetEnabled = resetEnabled,
                    playPauseEnabled = gameState != GameState.Finished,
                    onPlayPauseClick = onPlayPauseClick,
                    onEditClick = onEditClick,
                    onSettingsClick = onSettingsClick,
                    onRefreshClick =  { openResetDialog = true }
                )

                // Player 2 ui
                PlayerUi(
                    modifier = Modifier
                        .weight(4.5F, fill = true)
                        .background(backgroundColor),
                    enabled = player2Enabled,
                    preferenceSound = preferenceSound,
                    preferenceShowGameInfoLandscape = preferenceShowGameInfoLandscape,
                    preferenceShowGameInfoPortrait = preferenceShowGameInfoPortrait,
                    preferenceShowOpponentTimeLandscape = preferenceShowOpponentTimeLandscape,
                    preferenceShowOpponentTimePortrait = preferenceShowOpponentTimePortrait,
                    lostGame = (gameState == GameState.Finished && playersTurn == Player.Player2),
                    timerStatePrimary = timer2State,
                    moveNumber = movesPlayer2,
                    timerStateSecondary = timer1State,
                    gameState = gameState,
                    onClick = onPlayer2Click
                )
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                // Player 2 ui
                PlayerUi(
                    modifier = Modifier
                        .weight(4.5F, fill = true)
                        .background(backgroundColor)
                        .rotate(180F),
                    enabled = player2Enabled,
                    preferenceSound = preferenceSound,
                    preferenceShowGameInfoLandscape = preferenceShowGameInfoLandscape,
                    preferenceShowGameInfoPortrait = preferenceShowGameInfoPortrait,
                    preferenceShowOpponentTimeLandscape = preferenceShowOpponentTimeLandscape,
                    preferenceShowOpponentTimePortrait = preferenceShowOpponentTimePortrait,
                    lostGame = (gameState == GameState.Finished && playersTurn == Player.Player2),
                    timerStatePrimary = timer2State,
                    moveNumber = movesPlayer2,
                    timerStateSecondary = timer1State,
                    gameState = gameState,
                    onClick = onPlayer2Click
                )

                // General ui (action buttons)
                GeneralUi(
                    modifier = Modifier
                        .weight(1F, fill = true)
                        .background(backgroundColor),
                    showPause = showPause,
                    resetEnabled = resetEnabled,
                    playPauseEnabled = gameState != GameState.Finished,
                    onPlayPauseClick = onPlayPauseClick,
                    onEditClick = onEditClick,
                    onSettingsClick = onSettingsClick,
                    onRefreshClick = { openResetDialog = true }
                )

                // Player 1 ui
                PlayerUi(
                    modifier = Modifier
                        .weight(4.5F, fill = true)
                        .background(backgroundColor),
                    enabled = player1Enabled,
                    preferenceSound = preferenceSound,
                    preferenceShowGameInfoLandscape = preferenceShowGameInfoLandscape,
                    preferenceShowGameInfoPortrait = preferenceShowGameInfoPortrait,
                    preferenceShowOpponentTimeLandscape = preferenceShowOpponentTimeLandscape,
                    preferenceShowOpponentTimePortrait = preferenceShowOpponentTimePortrait,
                    lostGame = (gameState == GameState.Finished && playersTurn == Player.Player1),
                    timerStatePrimary = timer1State,
                    moveNumber = movesPlayer1,
                    timerStateSecondary = timer2State,
                    gameState = gameState,
                    onClick = onPlayer1Click
                )
            }
        }

        // Reset dialog
        if (openResetDialog) {
            AlertDialog(
                text = {
                    Text(text = "Reset game?")
                },
                onDismissRequest = { openResetDialog = false },
                dismissButton = {
                    Button(onClick = { openResetDialog = false }) {
                        Text(text = "Cancel")
                    }
                },
                confirmButton = {
                    Button(onClick = {
                        onRefreshClick()
                        openResetDialog = false
                    }) {
                        Text(text = "Reset")
                    }
                }
            )
        }
    }
}

@Composable
fun PlayerUi(
    modifier: Modifier,
    preferenceSound: Boolean = true,
    preferenceShowOpponentTimePortrait: Boolean = true,
    preferenceShowOpponentTimeLandscape: Boolean = false,
    preferenceShowGameInfoPortrait: Boolean = true,
    preferenceShowGameInfoLandscape: Boolean = true,
    enabled: Boolean = true,
    timerStatePrimary: String,
    moveNumber: Int,
    timerStateSecondary: String,
    gameState: GameState,
    lostGame: Boolean = false,
    onClick: () -> Unit
) {
    val context = LocalContext.current
    //val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
    val mediaPlayer = MediaPlayer.create(context,R.raw.slap)

    Box(
        modifier = modifier.then(
            Modifier
                .fillMaxSize()
                .clickable(
                    enabled = enabled
                ) {
                    if (preferenceSound) {
                        mediaPlayer.start()
                        //audioManager.playSoundEffect(AudioManager.FX_KEY_CLICK, volumeLevel)
                    }
                    onClick()
                }
        )
    ) {
        var textColor = MaterialTheme.colorScheme.primary
        var backgroundColor = MaterialTheme.colorScheme.onPrimary

        if (!enabled) {
            textColor = textColor.copy(
                alpha = textColor.alpha * 0.75F
            )
            backgroundColor = backgroundColor.copy(
                alpha = backgroundColor.alpha * 0.75F
            )
        }

        HomeScreenPlayerContent(
            timerStatePrimary = timerStatePrimary,
            moveNumber = moveNumber,
            timerStateSecondary = timerStateSecondary,
            gameState = gameState,
            textColor = textColor,
            backgroundColor = backgroundColor,
            enabled = enabled,
            lostGame = lostGame,
            preferenceShowGameInfoLandscape = preferenceShowGameInfoLandscape,
            preferenceShowGameInfoPortrait = preferenceShowGameInfoPortrait,
            preferenceShowOpponentTimeLandscape = preferenceShowOpponentTimeLandscape,
            preferenceShowOpponentTimePortrait = preferenceShowOpponentTimePortrait
        )
    }
}

@Composable
fun GeneralUi(
    modifier: Modifier,
    showPause: Boolean = false,
    resetEnabled: Boolean = true,
    playPauseEnabled: Boolean = true,
    onPlayPauseClick: () -> Unit,
    onEditClick: () -> Unit,
    onSettingsClick: () -> Unit,
    onRefreshClick: () -> Unit
) {
    BoxWithConstraints(
        modifier = modifier.then(
            Modifier.fillMaxSize()
        ),
        contentAlignment = Alignment.Center
    ) {
        val scope = this
        ButtonLayout(
            parentHeight = scope.minHeight,
            showPause = showPause,
            resetEnabled = resetEnabled,
            playPauseEnabled = playPauseEnabled,
            onPlayPauseClick = onPlayPauseClick,
            onEditClick = onEditClick,
            onSettingsClick = onSettingsClick,
            onRefreshClick = onRefreshClick
        )
    }
}
package eu.merklaafe.chessclockdigital.presentation.generic

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import eu.merklaafe.chessclockdigital.R

@Composable
fun ButtonLayout(
    parentHeight: Dp,
    iconColor: Color = MaterialTheme.colorScheme.onSecondary,
    backgroundBrush: Brush = Brush.linearGradient(listOf(MaterialTheme.colorScheme.secondary,MaterialTheme.colorScheme.secondary)),
    backgroundShape: Shape = RoundedCornerShape(20),
    borderStroke: BorderStroke = BorderStroke(0.dp, MaterialTheme.colorScheme.outline),
    showPause: Boolean = false,
    resetEnabled: Boolean = true,
    playPauseEnabled: Boolean = true,
    onPlayPauseClick: () -> Unit,
    onEditClick: () -> Unit,
    onSettingsClick: () -> Unit,
    onRefreshClick: () -> Unit
) {
    val orientation = LocalConfiguration.current.orientation
    if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
        ButtonLayoutLandscape(
            modifier = Modifier
                .height((parentHeight/10)*9)
                .fillMaxWidth(),
            iconColor = iconColor,
            backgroundBrush = backgroundBrush,
            backgroundShape = backgroundShape,
            borderStroke = borderStroke,
            showPause = showPause,
            resetEnabled = resetEnabled,
            playPauseEnabled = playPauseEnabled,
            onPlayPauseClick = onPlayPauseClick,
            onEditClick = onEditClick,
            onSettingsClick = onSettingsClick,
            onRefreshClick = onRefreshClick
        )
    } else {
        ButtonLayoutPortrait(
            modifier = Modifier.height((parentHeight/10)*9),
            iconColor = iconColor,
            backgroundBrush = backgroundBrush,
            backgroundShape = backgroundShape,
            borderStroke = borderStroke,
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

@Composable
fun ButtonLayoutPortrait(
    modifier: Modifier = Modifier,
    iconColor: Color = MaterialTheme.colorScheme.onTertiary,
    backgroundBrush: Brush = Brush.linearGradient(listOf(Color.Gray,Color.DarkGray)),
    backgroundShape: Shape = RoundedCornerShape(20),
    borderStroke: BorderStroke = BorderStroke(1.dp, Color.LightGray),
    showPause: Boolean = false,
    resetEnabled: Boolean = true,
    playPauseEnabled: Boolean = true,
    onPlayPauseClick: () -> Unit,
    onEditClick: () -> Unit,
    onSettingsClick: () -> Unit,
    onRefreshClick: () -> Unit
) {
    Column(
        modifier = Modifier.padding(start = 10.dp, end = 10.dp)
    ) {
        Row {
            SettingsButton (
                modifier = Modifier.weight(1F).then(modifier),
                iconColor = iconColor,
                backgroundBrush = backgroundBrush,
                backgroundShape = backgroundShape,
                borderStroke = borderStroke,
                onClick = onSettingsClick
            )
            PlayPauseButton (
                modifier = Modifier.weight(1F).then(modifier),
                enabled = playPauseEnabled,
                showPause = showPause,
                iconColor = iconColor,
                backgroundShape = backgroundShape,
                borderStroke = borderStroke,
                onClick = onPlayPauseClick
            )
            ReplayButton (
                modifier = Modifier.weight(1F).then(modifier),
                enabled = resetEnabled,
                iconColor = iconColor,
                backgroundShape = backgroundShape,
                borderStroke = borderStroke,
                onClick = onRefreshClick
            )
            EditButton (
                modifier = Modifier.weight(1F).then(modifier),
                iconColor = iconColor,
                backgroundBrush = backgroundBrush,
                backgroundShape = backgroundShape,
                borderStroke = borderStroke,
                onClick = onEditClick
            )
        }
    }
}

@Composable
fun ButtonLayoutLandscape(
    modifier: Modifier = Modifier,
    iconColor: Color = Color.Black,
    backgroundBrush: Brush = Brush.linearGradient(listOf(Color.Gray,Color.DarkGray)),
    backgroundShape: Shape = RoundedCornerShape(20),
    borderStroke: BorderStroke = BorderStroke(1.dp, Color.LightGray),
    showPause: Boolean = false,
    resetEnabled: Boolean = true,
    playPauseEnabled: Boolean = true,
    onPlayPauseClick: () -> Unit,
    onEditClick: () -> Unit,
    onSettingsClick: () -> Unit,
    onRefreshClick: () -> Unit
) {
    Column(
        modifier = Modifier.padding(top = 10.dp, bottom = 10.dp)
    ) {
        SettingsButton (
            modifier = Modifier.weight(1F).then(modifier),
            iconColor = iconColor,
            backgroundBrush = backgroundBrush,
            backgroundShape = backgroundShape,
            borderStroke = borderStroke,
            onClick = onSettingsClick
        )
        PlayPauseButton (
            modifier = Modifier.weight(1F).then(modifier),
            enabled = playPauseEnabled,
            showPause = showPause,
            iconColor = iconColor,
            backgroundShape = backgroundShape,
            borderStroke = borderStroke,
            onClick = onPlayPauseClick
        )
        ReplayButton (
            modifier = Modifier.weight(1F).then(modifier),
            enabled = resetEnabled,
            iconColor = iconColor,
            backgroundShape = backgroundShape,
            borderStroke = borderStroke,
            onClick = onRefreshClick
        )
        EditButton (
            modifier = Modifier.weight(1F).then(modifier),
            iconColor = iconColor,
            backgroundBrush = backgroundBrush,
            backgroundShape = backgroundShape,
            borderStroke = borderStroke,
            onClick = onEditClick
        )
    }
}

@Composable
fun ReplayButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    iconColor: Color = Color.Black,
    backgroundColor: Color = MaterialTheme.colorScheme.secondary,
    backgroundShape: Shape = RoundedCornerShape(20),
    borderStroke: BorderStroke = BorderStroke(1.dp, Color.LightGray),
    onClick: () -> Unit
) {
    var reactiveIconColor = iconColor
    var reactiveBackgroundColor = backgroundColor

    if (!enabled) {
        reactiveIconColor = reactiveIconColor.copy(
            alpha = reactiveIconColor.alpha * 0.75F
        )
        reactiveBackgroundColor = reactiveBackgroundColor.copy(
            alpha = reactiveBackgroundColor.alpha * 0.75F
        )
    }

    IconButton(
        modifier = Modifier
            .padding(2.dp)
            .background(reactiveBackgroundColor, backgroundShape)
            .border(borderStroke,backgroundShape)
            .then(modifier),
        enabled = enabled,
        onClick = onClick
    ) {
        Icon(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(id = R.drawable.baseline_replay_24),
            contentDescription = "Replay",
            tint = reactiveIconColor
        )
    }
}

@Composable
fun PlayPauseButton(
    modifier: Modifier = Modifier,
    showPause: Boolean = false,
    enabled: Boolean = true,
    iconColor: Color = Color.Black,
    backgroundColor: Color = MaterialTheme.colorScheme.secondary,
    backgroundShape: Shape = RoundedCornerShape(20),
    borderStroke: BorderStroke = BorderStroke(1.dp, Color.LightGray),
    onClick: () -> Unit
) {
    var reactiveIconColor = iconColor
    var reactiveBackgroundColor = backgroundColor

    if (!enabled) {
        reactiveIconColor = reactiveIconColor.copy(
            alpha = reactiveIconColor.alpha * 0.75F
        )
        reactiveBackgroundColor = reactiveBackgroundColor.copy(
            alpha = reactiveBackgroundColor.alpha * 0.75F
        )
    }


    IconButton(
        modifier = Modifier
            .padding(2.dp)
            .background(reactiveBackgroundColor,backgroundShape)
            .border(borderStroke,backgroundShape)
            .then(modifier),
        enabled = enabled,
        onClick = onClick
    ) {
        if (showPause) {
            Icon(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(id = R.drawable.baseline_pause_circle_outline_24),
                contentDescription = "Pause",
                tint = reactiveIconColor
            )
        } else {
            Icon(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(id = R.drawable.baseline_play_circle_outline_24),
                contentDescription = "Play",
                tint = reactiveIconColor
            )
        }
    }
}

@Composable
fun EditButton(
    modifier: Modifier = Modifier,
    iconColor: Color = Color.Black,
    backgroundBrush: Brush = Brush.linearGradient(listOf(Color.Gray,Color.DarkGray)),
    backgroundShape: Shape = RoundedCornerShape(20),
    borderStroke: BorderStroke = BorderStroke(1.dp, Color.LightGray),
    onClick: () -> Unit
) {
    IconButton(
        modifier = Modifier
            .padding(2.dp)
            .background(backgroundBrush, backgroundShape)
            .border(borderStroke,backgroundShape)
            .then(modifier),
        onClick = onClick
    ) {
        Row(
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(id = R.drawable.baseline_more_time_24),
                contentDescription = "Edit",
                tint = iconColor
            )
        }
    }
}

@Composable
fun SettingsButton(
    modifier: Modifier = Modifier,
    iconColor: Color = Color.Black,
    backgroundBrush: Brush = Brush.linearGradient(listOf(Color.Gray,Color.DarkGray)),
    backgroundShape: Shape = RoundedCornerShape(20),
    borderStroke: BorderStroke = BorderStroke(1.dp, Color.LightGray),
    onClick: () -> Unit
) {
    IconButton(
        modifier = Modifier
            .padding(2.dp)
            .background(backgroundBrush, backgroundShape)
            .border(borderStroke,backgroundShape)
            .then(modifier),
        onClick = onClick
    ) {
        Icon(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(id = R.drawable.baseline_settings_24),
            contentDescription = "Settings",
            tint = iconColor
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ButtonPreview(

) {
    ButtonLayoutPortrait(
        onPlayPauseClick = { /*TODO*/ },
        onEditClick = { /*TODO*/ },
        onSettingsClick = { /*TODO*/ },
        onRefreshClick = { /*TODO*/ }
    )
//    Column(
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.SpaceEvenly
//    ) {
//        ReplayButton(modifier = Modifier.weight(1F)) {}
//        PlusButton(modifier = Modifier.weight(1F)) {}
//        MinusButton(modifier = Modifier.weight(1F)) {}
//        LeftButton(modifier = Modifier.weight(1F)) {}
//        RightButton(modifier = Modifier.weight(1F)) {}
//        EditButton(modifier = Modifier.weight(1F)) {}
//        SettingsButton(modifier = Modifier.weight(1F)) {}
//        PlayPauseButton(modifier = Modifier.weight(1F)) {}
//        PlayPauseButton(modifier = Modifier.weight(1F), showPause = true) {}
//    }
}

@Preview (showBackground = true, device = Devices.TABLET)
@Composable
fun ButtonPreviewLandscape(

)
{
    ButtonLayoutLandscape(
        onPlayPauseClick = { /*TODO*/ },
        onEditClick = { /*TODO*/ },
        onSettingsClick = { /*TODO*/ },
        onRefreshClick = { /*TODO*/ }
    )
}


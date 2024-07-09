package eu.merklaafe.chessclockdigital.presentation.screens.settings

import android.content.Context
import android.media.AudioManager
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.IconToggleButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Shapes
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import eu.merklaafe.chessclockdigital.R
import eu.merklaafe.chessclockdigital.model.ThemeConfig

@Composable
fun SettingsContent (
    paddingValues: PaddingValues,
    textColor: Color = MaterialTheme.colorScheme.primary,

    preferenceSound: Boolean,
    preferenceFullScreen: Boolean,
    preferenceShowOpponentTimePortrait: Boolean,
    preferenceShowOpponentTimeLandscape: Boolean,
    preferenceShowGameInfoPortrait: Boolean,
    preferenceShowGameInfoLandscape: Boolean,
    preferenceThemeConfig: ThemeConfig,

    onPreferenceSoundChange: (Boolean) -> Unit,
    onPreferenceFullScreenChange: (Boolean) -> Unit,
    onPreferenceShowOpponentTimePortraitChange: (Boolean) -> Unit,
    onPreferenceShowOpponentTimeLandscapeChange: (Boolean) -> Unit,
    onPreferenceShowGameInfoPortraitChange: (Boolean) -> Unit,
    onPreferenceShowGameInfoLandscapeChange: (Boolean) -> Unit,
    onPreferenceThemeConfigChange: (ThemeConfig) -> Unit,

    feedbackSystemSoundMuted: () -> Unit,
) {
    val context = LocalContext.current
    val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .imePadding()
            .navigationBarsPadding()
            .padding(paddingValues)
            .padding(horizontal = 10.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = "General",
                    color = textColor,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    fontWeight = FontWeight.SemiBold,
                    textDecoration = TextDecoration.Underline
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = "Full screen mode:",
                    color = textColor,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
                Switch(
                    checked = preferenceFullScreen,
                    onCheckedChange = {
                        onPreferenceFullScreenChange(it)
                    }
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = "Sound effects:",
                    color = textColor,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
                Switch(
                    checked = preferenceSound,
                    onCheckedChange = {
                        onPreferenceSoundChange(it)
                        if (it && audioManager.getStreamVolume(AudioManager.STREAM_MUSIC) == 0) {
                            feedbackSystemSoundMuted()
                        }
                    }
                )
            }

//            HorizontalDivider(
//                modifier = Modifier.padding(top = 5.dp),
//                thickness = 3.dp,
//                color = MaterialTheme.colorScheme.outline
//            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = "Game screen (Portrait)",
                    color = textColor,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    fontWeight = FontWeight.SemiBold,
                    textDecoration = TextDecoration.Underline
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = "Show opponent time:",
                    color = textColor,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
                Switch(
                    checked = preferenceShowOpponentTimePortrait,
                    onCheckedChange = {
                        onPreferenceShowOpponentTimePortraitChange(it)
                    }
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = "Show game state info:",
                    color = textColor,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
                Switch(
                    checked = preferenceShowGameInfoPortrait,
                    onCheckedChange = {
                        onPreferenceShowGameInfoPortraitChange(it)
                    }
                )
            }

//            HorizontalDivider(
//                modifier = Modifier.padding(top = 5.dp),
//                thickness = 3.dp,
//                color = MaterialTheme.colorScheme.outline
//            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = "Game screen (Landscape)",
                    color = textColor,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    fontWeight = FontWeight.SemiBold,
                    textDecoration = TextDecoration.Underline
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = "Show opponent time:",
                    color = textColor,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
                Switch(
                    checked = preferenceShowOpponentTimeLandscape,
                    onCheckedChange = {
                        onPreferenceShowOpponentTimeLandscapeChange(it)
                    }
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = "Show game state info:",
                    color = textColor,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
                Switch(
                    checked = preferenceShowGameInfoLandscape,
                    onCheckedChange = {
                        onPreferenceShowGameInfoLandscapeChange(it)
                    }
                )
            }

//            HorizontalDivider(
//                modifier = Modifier.padding(top = 5.dp),
//                thickness = 3.dp,
//                color = MaterialTheme.colorScheme.outline
//            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = "Theme",
                    color = textColor,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    fontWeight = FontWeight.SemiBold,
                    textDecoration = TextDecoration.Underline
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth().padding(end = 2.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = "System:",
                    color = textColor,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )

                IconToggleButton(
                    modifier = Modifier
                        .border(
                            width = 1.dp,
                            color = if(preferenceThemeConfig == ThemeConfig.System) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface,
                            shape = RoundedCornerShape(100f))
                    ,
                    checked = preferenceThemeConfig == ThemeConfig.System,
                    onCheckedChange = {onPreferenceThemeConfigChange(ThemeConfig.System)},
                    colors = IconToggleButtonColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                        contentColor = MaterialTheme.colorScheme.outline,
                        disabledContainerColor = MaterialTheme.colorScheme.surface,
                        disabledContentColor = MaterialTheme.colorScheme.outline,
                        checkedContainerColor = MaterialTheme.colorScheme.surface,
                        checkedContentColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Icon(
                        modifier = Modifier.fillMaxSize(),
                        painter = painterResource(id = R.drawable.outline_phone_android_24),
                        contentDescription = "System"
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth().padding(end = 2.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = "Light:",
                    color = textColor,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )

                IconToggleButton(
                    modifier = Modifier
                        .border(
                            width = 1.dp,
                            color = if(preferenceThemeConfig == ThemeConfig.Light) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface,
                            shape = RoundedCornerShape(100f))
                    ,
                    checked = preferenceThemeConfig == ThemeConfig.Light,
                    onCheckedChange = {onPreferenceThemeConfigChange(ThemeConfig.Light)},
                    colors = IconToggleButtonColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                        contentColor = MaterialTheme.colorScheme.outline,
                        disabledContainerColor = MaterialTheme.colorScheme.surface,
                        disabledContentColor = MaterialTheme.colorScheme.outline,
                        checkedContainerColor = MaterialTheme.colorScheme.surface,
                        checkedContentColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Icon(
                        modifier = Modifier.fillMaxSize(),
                        painter = painterResource(id = R.drawable.outline_light_mode_24),
                        contentDescription = "Light"
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth().padding(end = 2.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = "Dark:",
                    color = textColor,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )

                IconToggleButton(
                    modifier = Modifier
                        .border(
                            width = 1.dp,
                            color = if(preferenceThemeConfig == ThemeConfig.Dark) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface,
                            shape = RoundedCornerShape(100f))
                    ,
                    checked = preferenceThemeConfig == ThemeConfig.Dark,
                    onCheckedChange = {onPreferenceThemeConfigChange(ThemeConfig.Dark)},
                    colors = IconToggleButtonColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                        contentColor = MaterialTheme.colorScheme.outline,
                        disabledContainerColor = MaterialTheme.colorScheme.surface,
                        disabledContentColor = MaterialTheme.colorScheme.outline,
                        checkedContainerColor = MaterialTheme.colorScheme.surface,
                        checkedContentColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Icon(
                        modifier = Modifier.fillMaxSize(),
                        painter = painterResource(id = R.drawable.outline_dark_mode_24),
                        contentDescription = "Dark"
                    )
                }
            }
        }
    }
}
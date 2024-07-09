package eu.merklaafe.chessclockdigital.presentation.generic

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp

@Composable
fun DpToSp(dp: Dp) = with(LocalDensity.current){dp.toSp()}
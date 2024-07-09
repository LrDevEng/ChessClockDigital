package eu.merklaafe.chessclockdigital.presentation.generic

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFontFamilyResolver
import androidx.compose.ui.text.Paragraph
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.sp
import kotlin.math.ceil

@Composable
fun AutoSizeSingleLineText(
    modifier: Modifier = Modifier,
    text: String,
    minFontSize: TextUnit = 5.sp,
    maxFontSize: TextUnit = 500.sp,
    fixedSize: Pair<TextUnit?,TextUnit?> = null to null,
    increasingFactor: Float = 1.1f,
    decreasingFactor: Float = 0.9f,
    fineTuningGranularity: TextUnit = 2.sp,
    color: Color = Color.Unspecified,
    fontStyle: FontStyle? = null,
    fontWeight: FontWeight? = null,
    fontFamily: FontFamily? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign = TextAlign.Center,
    contentAlignment: Alignment? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    style: TextStyle = LocalTextStyle.current,
    calculatedFontSize: (Pair<TextUnit?,TextUnit?>) -> Unit = {}
) {
    val orientation = LocalConfiguration.current.orientation
    var calculations: Pair<TextUnit?,TextUnit?> = null to null

    if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
        AutomaticSingleLineText(
            modifier = modifier,
            text = text,
            minFontSize = minFontSize,
            maxFontSize = maxFontSize,
            fixedSize = fixedSize.second,
            increasingFactor = increasingFactor,
            decreasingFactor = decreasingFactor,
            fineTuningGranularity = fineTuningGranularity,
            color = color,
            fontStyle = fontStyle,
            fontWeight = fontWeight,
            fontFamily = fontFamily,
            letterSpacing = letterSpacing,
            textDecoration = textDecoration,
            textAlign = textAlign,
            contentAlignment = contentAlignment,
            lineHeight = lineHeight,
            onTextLayout = onTextLayout,
            style = style,
            orientation = Configuration.ORIENTATION_LANDSCAPE,
            calculatedFontSize = {
                calculations = calculations.first to it
                calculatedFontSize(calculations)
            }
        )
    } else {
        AutomaticSingleLineText(
            modifier = modifier,
            text = text,
            minFontSize = minFontSize,
            maxFontSize = maxFontSize,
            fixedSize = fixedSize.first,
            increasingFactor = increasingFactor,
            decreasingFactor = decreasingFactor,
            fineTuningGranularity = fineTuningGranularity,
            color = color,
            fontStyle = fontStyle,
            fontWeight = fontWeight,
            fontFamily = fontFamily,
            letterSpacing = letterSpacing,
            textDecoration = textDecoration,
            textAlign = textAlign,
            contentAlignment = contentAlignment,
            lineHeight = lineHeight,
            onTextLayout = onTextLayout,
            style = style,
            orientation = Configuration.ORIENTATION_PORTRAIT,
            calculatedFontSize = {
                calculations = it to calculations.second
                calculatedFontSize(calculations)
            }
        )
    }
}

@Composable
fun AutomaticSingleLineText(
    modifier: Modifier = Modifier,
    text: String,
    minFontSize: TextUnit = 5.sp,
    maxFontSize: TextUnit = 500.sp,
    fixedSize: TextUnit? = null,
    increasingFactor: Float = 1.1f,
    decreasingFactor: Float = 0.9f,
    fineTuningGranularity: TextUnit = 2.sp,
    color: Color = Color.Unspecified,
    fontStyle: FontStyle? = null,
    fontWeight: FontWeight? = null,
    fontFamily: FontFamily? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign = TextAlign.Center,
    contentAlignment: Alignment? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    style: TextStyle = LocalTextStyle.current,
    orientation: Int = 0,
    calculatedFontSize: (TextUnit) -> Unit,
) {
    var newFontSize by remember { mutableStateOf(minFontSize) }
    var newFontSizeFloat by rememberSaveable { mutableFloatStateOf(minFontSize.value) }
    var textLength by rememberSaveable { mutableIntStateOf(0) }
    val maxLines = 1
    val alignment: Alignment = contentAlignment ?: when (textAlign) {
        TextAlign.Left -> Alignment.TopStart
        TextAlign.Right -> Alignment.TopEnd
        TextAlign.Center -> Alignment.Center
        TextAlign.Justify -> Alignment.TopCenter
        TextAlign.Start -> Alignment.TopStart
        TextAlign.End -> Alignment.TopEnd
        else -> Alignment.TopStart
    }

    BoxWithConstraints(
        modifier = modifier,
        contentAlignment = alignment,
    ) {
        if (fixedSize != null) {
            newFontSize = fixedSize
        } else {
            if (text.length != textLength) {
                textLength = text.length
                val calculateIntrinsics = @Composable {
                    val mergedStyle = style.merge(
                        TextStyle(
                            color = color,
                            fontSize = newFontSize,
                            fontWeight = fontWeight,
                            textAlign = textAlign,
                            lineHeight = lineHeight,
                            fontFamily = fontFamily,
                            textDecoration = textDecoration,
                            fontStyle = fontStyle,
                            letterSpacing = letterSpacing
                        )
                    )
                    Paragraph(
                        text = text,
                        style = mergedStyle,
                        constraints = Constraints(maxWidth = ceil(LocalDensity.current.run { maxWidth.toPx() }).toInt()),
                        density = LocalDensity.current,
                        fontFamilyResolver = LocalFontFamilyResolver.current,
                        spanStyles = listOf(),
                        placeholders = listOf(),
                        maxLines = maxLines,
                        ellipsis = false
                    )
                }

                var intrinsics = calculateIntrinsics()

                with(LocalDensity.current) {
                    // Increase font size by 10% until it exceeds bounds
                    while (!intrinsics.didExceedMaxLines && maxHeight > intrinsics.height.toDp() && maxWidth > intrinsics.maxIntrinsicWidth.toDp() && newFontSize * increasingFactor < maxFontSize) {
                        newFontSize *= increasingFactor
                        intrinsics = calculateIntrinsics()
                    }

                    Log.d(
                        "AutoSizeText",
                        "Orientation: $orientation -> New font size after increase: $newFontSize for: $text"
                    )

                    // Decrease font size by 10% until it fits bounds
                    while ((intrinsics.didExceedMaxLines || maxHeight < intrinsics.height.toDp() || maxWidth < intrinsics.minIntrinsicWidth.toDp()) && newFontSize * decreasingFactor > minFontSize) {
                        newFontSize *= decreasingFactor
                        intrinsics = calculateIntrinsics()
                    }

                    Log.d(
                        "AutoSizeText",
                        "Orientation: $orientation -> New font size after decrease: $newFontSize for: $text"
                    )

                    // Fine tuning
                    while (!intrinsics.didExceedMaxLines && maxHeight > intrinsics.height.toDp() && maxWidth > intrinsics.maxIntrinsicWidth.toDp() && TextUnit(
                            newFontSize.value + fineTuningGranularity.value,
                            TextUnitType.Sp
                        ) < maxFontSize
                    ) {
                        newFontSize =
                            TextUnit(
                                newFontSize.value + fineTuningGranularity.value,
                                TextUnitType.Sp
                            )
                        intrinsics = calculateIntrinsics()

                        Log.d(
                            "AutoSizeText",
                            "Orientation: $orientation -> New font size after fine tuning: $newFontSize for: $text"
                        )
                    }
                    newFontSize =
                        TextUnit(newFontSize.value - fineTuningGranularity.value, TextUnitType.Sp)
                    newFontSizeFloat = newFontSize.value
                    calculatedFontSize(newFontSize)
                }
            } else {
                newFontSize = newFontSizeFloat.sp
            }
        }

        Text(
            text = text,
            color = color,
            fontSize = newFontSize,
            fontStyle = fontStyle,
            fontWeight = fontWeight,
            fontFamily = fontFamily,
            letterSpacing = letterSpacing,
            textDecoration = textDecoration,
            textAlign = textAlign,
            lineHeight = lineHeight,
            onTextLayout = onTextLayout,
            maxLines = maxLines,
            style = style,
            overflow = TextOverflow.Ellipsis
        )
    }
}




package eu.merklaafe.chessclockdigital.ui.theme

import android.app.Activity
import android.os.Build
import android.util.Log
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import eu.merklaafe.chessclockdigital.model.ThemeConfig

private val lightScheme = lightColorScheme(
    primary = Color(0xff445e91),
    onPrimary = Color(0xffffffff),
    primaryContainer = Color(0xffd8e2ff),
    onPrimaryContainer = Color(0xff001a41),
    secondary = Color(0xff575e71),
    onSecondary = Color(0xffffffff),
    secondaryContainer = Color(0xffdbe2f9),
    onSecondaryContainer = Color(0xff141b2c),
    tertiary = Color(0xff715573),
    onTertiary = Color(0xffffffff),
    tertiaryContainer = Color(0xfffbd7fc),
    onTertiaryContainer = Color(0xff29132d),
    error = Color(0xffb3261e),
    onError = Color(0xffffffff),
    errorContainer = Color(0xfff9dedc),
    onErrorContainer = Color(0xff410e0b),
    background = Color(0xfffaf9fd),
    onBackground = Color(0xff1b1b1f),
    surface = Color(0xfffaf9fd),
    onSurface = Color(0xff1b1b1f),
    surfaceVariant = Color(0xffe1e2ec),
    onSurfaceVariant = Color(0xff44474f),
    outline = Color(0xff72747d),
    outlineVariant = Color(0xffc4c7c5),
    scrim = Color(0xff000000),
    inverseSurface = Color(0xff121316),
    inverseOnSurface = Color(0xffe3e2e6),
    inversePrimary = Color(0xffadc6ff),
    surfaceDim = Color(0xffdbd9dd),
    surfaceBright = Color(0xfffaf9fd),
    surfaceContainerLowest = Color(0xffffffff),
    surfaceContainerLow = Color(0xfff5f3f7),
    surfaceContainer = Color(0xffefedf1),
    surfaceContainerHigh = Color(0xffe9e7ec),
    surfaceContainerHighest = Color(0xffe3e2e6)
)

private val darkScheme = darkColorScheme(
    primary = Color(0xffadc6ff),
    onPrimary = Color(0xff102f60),
    primaryContainer = Color(0xff2b4678),
    onPrimaryContainer = Color(0xffd8e2ff),
    secondary = Color(0xffbfc6dc),
    onSecondary = Color(0xff293041),
    secondaryContainer = Color(0xff3f4759),
    onSecondaryContainer = Color(0xffdbe2f9),
    tertiary = Color(0xffdebcdf),
    onTertiary = Color(0xff402843),
    tertiaryContainer = Color(0xff583e5b),
    onTertiaryContainer = Color(0xfffbd7fc),
    error = Color(0xfff2b8b5),
    onError = Color(0xff601410),
    errorContainer = Color(0xff8c1d18),
    onErrorContainer = Color(0xfff9dedc),
    background = Color(0xff121316),
    onBackground = Color(0xffe3e2e6),
    surface = Color(0xff121316),
    onSurface = Color(0xffe3e2e6),
    surfaceVariant = Color(0xff44474f),
    onSurfaceVariant = Color(0xffc4c6d0),
    outline = Color(0xff72747d),
    outlineVariant = Color(0xff444746),
    scrim = Color(0xff000000),
    inverseSurface = Color(0xfffaf9fd),
    inverseOnSurface = Color(0xff1b1b1f),
    inversePrimary = Color(0xff445e91),
    surfaceDim = Color(0xff121316),
    surfaceBright = Color(0xff38393c),
    surfaceContainerLowest = Color(0xff0d0e11),
    surfaceContainerLow = Color(0xff1b1b1f),
    surfaceContainer = Color(0xff1f1f23),
    surfaceContainerHigh = Color(0xff292a2d),
    surfaceContainerHighest = Color(0xff343538)
)

@OptIn(ExperimentalStdlibApi::class)
@Composable
fun ChessClockDigitalTheme(
    systemDarkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    themeConfig: ThemeConfig = ThemeConfig.System,
    content: @Composable() () -> Unit
) {
    val darkTheme = when(themeConfig) {
        ThemeConfig.System -> systemDarkTheme
        ThemeConfig.Dark -> true
        ThemeConfig.Light -> false
    }

    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> darkScheme
        else -> lightScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )

//    Log.d("ColorScheme" ,
//            "primary = Color(0x${colorScheme.primary.value.toHexString().dropLast(8)}), \n" +
//                "onPrimary = Color(0x${colorScheme.onPrimary.value.toHexString().dropLast(8)}), \n" +
//                "primaryContainer = Color(0x${colorScheme.primaryContainer.value.toHexString().dropLast(8)}), \n" +
//                "onPrimaryContainer = Color(0x${colorScheme.onPrimaryContainer.value.toHexString().dropLast(8)}), \n" +
//
//                "secondary = Color(0x${colorScheme.secondary.value.toHexString().dropLast(8)}), \n" +
//                "onSecondary = Color(0x${colorScheme.onSecondary.value.toHexString().dropLast(8)}), \n" +
//                "secondaryContainer = Color(0x${colorScheme.secondaryContainer.value.toHexString().dropLast(8)}), \n" +
//                "onSecondaryContainer = Color(0x${colorScheme.onSecondaryContainer.value.toHexString().dropLast(8)}), \n" +
//
//                "tertiary = Color(0x${colorScheme.tertiary.value.toHexString().dropLast(8)}), \n" +
//                "onTertiary = Color(0x${colorScheme.onTertiary.value.toHexString().dropLast(8)}), \n" +
//                "tertiaryContainer = Color(0x${colorScheme.tertiaryContainer.value.toHexString().dropLast(8)}), \n" +
//                "onTertiaryContainer = Color(0x${colorScheme.onTertiaryContainer.value.toHexString().dropLast(8)}), \n" +
//
//                "error = Color(0x${colorScheme.error.value.toHexString().dropLast(8)}), \n" +
//                "onError = Color(0x${colorScheme.onError.value.toHexString().dropLast(8)}), \n" +
//                "errorContainer = Color(0x${colorScheme.errorContainer.value.toHexString().dropLast(8)}), \n" +
//                "onErrorContainer = Color(0x${colorScheme.onErrorContainer.value.toHexString().dropLast(8)}), \n" +
//
//                    "background = Color(0x${colorScheme.background.value.toHexString().dropLast(8)}), \n" +
//                    "onBackground = Color(0x${colorScheme.onBackground.value.toHexString().dropLast(8)}), \n" +
//
//                    "surface = Color(0x${colorScheme.surface.value.toHexString().dropLast(8)}), \n" +
//                    "onSurface = Color(0x${colorScheme.onSurface.value.toHexString().dropLast(8)}), \n" +
//                    "surfaceVariant = Color(0x${colorScheme.surfaceVariant.value.toHexString().dropLast(8)}), \n" +
//                    "onSurfaceVariant = Color(0x${colorScheme.onSurfaceVariant.value.toHexString().dropLast(8)}), \n" +
//
//                    "outline = Color(0x${colorScheme.outline.value.toHexString().dropLast(8)}), \n" +
//                    "outlineVariant = Color(0x${colorScheme.outlineVariant.value.toHexString().dropLast(8)}), \n" +
//
//                    "scrim = Color(0x${colorScheme.scrim.value.toHexString().dropLast(8)}), \n" +
//
//                    "inverseSurface = Color(0x${colorScheme.inverseSurface.value.toHexString().dropLast(8)}), \n" +
//                    "inverseOnSurface = Color(0x${colorScheme.inverseOnSurface.value.toHexString().dropLast(8)}), \n" +
//                    "inversePrimary = Color(0x${colorScheme.inversePrimary.value.toHexString().dropLast(8)}), \n" +
//
//                    "surfaceDim = Color(0x${colorScheme.surfaceDim.value.toHexString().dropLast(8)}), \n" +
//                    "surfaceBright = Color(0x${colorScheme.surfaceBright.value.toHexString().dropLast(8)}), \n" +
//                    "surfaceContainerLowest = Color(0x${colorScheme.surfaceContainerLowest.value.toHexString().dropLast(8)}), \n" +
//                    "surfaceContainerLow = Color(0x${colorScheme.surfaceContainerLow.value.toHexString().dropLast(8)}), \n" +
//                    "surfaceContainer = Color(0x${colorScheme.surfaceContainer.value.toHexString().dropLast(8)}), \n" +
//                    "surfaceContainerHigh = Color(0x${colorScheme.surfaceContainerHigh.value.toHexString().dropLast(8)}), \n" +
//                    "surfaceContainerHighest = Color(0x${colorScheme.surfaceContainerHighest.value.toHexString().dropLast(8)})"
//    )
}
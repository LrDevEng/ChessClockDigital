package eu.merklaafe.chessclockdigital.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import eu.merklaafe.chessclockdigital.R

// Fonts
val defaultFamily = FontFamily(
    Font(R.font.ibm_plex_mono_regular, FontWeight.Normal),
    Font(R.font.ibm_plex_mono_thin, FontWeight.Thin),
    Font(R.font.ibm_plex_mono_extra_light, FontWeight.ExtraLight),
    Font(R.font.ibm_plex_mono_light, FontWeight.Light),
    Font(R.font.ibm_plex_mono_medium, FontWeight.Medium),
    Font(R.font.ibm_plex_mono_semi_bold, FontWeight.SemiBold),
    Font(R.font.ibm_plex_mono_bold, FontWeight.Bold)
)

// Set of Material typography styles to start with
private val defaultTypography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )

    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)

val Typography = Typography(
    displayLarge = defaultTypography.displayLarge.copy(fontFamily = defaultFamily),
    displayMedium = defaultTypography.displayMedium.copy(fontFamily = defaultFamily),
    displaySmall = defaultTypography.displaySmall.copy(fontFamily = defaultFamily),

    headlineLarge = defaultTypography.headlineLarge.copy(fontFamily = defaultFamily),
    headlineMedium = defaultTypography.headlineMedium.copy(fontFamily = defaultFamily),
    headlineSmall = defaultTypography.headlineSmall.copy(fontFamily = defaultFamily),

    titleLarge = defaultTypography.titleLarge.copy(fontFamily = defaultFamily),
    titleMedium = defaultTypography.titleMedium.copy(fontFamily = defaultFamily),
    titleSmall = defaultTypography.titleSmall.copy(fontFamily = defaultFamily),

    bodyLarge = defaultTypography.bodyLarge.copy(fontFamily = defaultFamily),
    bodyMedium = defaultTypography.bodyMedium.copy(fontFamily = defaultFamily),
    bodySmall = defaultTypography.bodySmall.copy(fontFamily = defaultFamily),

    labelLarge = defaultTypography.labelLarge.copy(fontFamily = defaultFamily),
    labelMedium = defaultTypography.labelMedium.copy(fontFamily = defaultFamily),
    labelSmall = defaultTypography.labelSmall.copy(fontFamily = defaultFamily)
)
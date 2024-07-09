package eu.merklaafe.chessclockdigital.util

import androidx.core.text.isDigitsOnly
import eu.merklaafe.chessclockdigital.util.Constants.MILLIS_PER_HOUR
import eu.merklaafe.chessclockdigital.util.Constants.MILLIS_PER_MINUTE
import eu.merklaafe.chessclockdigital.util.Constants.MILLIS_PER_SECOND

fun parseMillisToDigits(
    millis: Long,
    clipLeadingZeros: Boolean = false,
): String {
    var digits = ""

    val hours = millis / MILLIS_PER_HOUR
    val minutes = (millis % MILLIS_PER_HOUR) / MILLIS_PER_MINUTE
    var seconds = ((millis % MILLIS_PER_HOUR) % MILLIS_PER_MINUTE) / MILLIS_PER_SECOND

    // Convert to digits string
    if (hours > 0L || !clipLeadingZeros) {
        digits += "$hours:"
        digits += String.format("%02d", minutes) + ":"
        digits += String.format("%02d", seconds)
    } else if (minutes > 0L) {
        digits += "$minutes:"
        digits += String.format("%02d", seconds)
    } else {
        digits = "0:"
        digits += String.format("%02d", seconds)
    }

    return digits
}

fun parseDigitsToMillis(
    digitsHour: String = "",
    digitsMinute: String = "",
    digitsSecond: String = ""
): Long {
    var millis = 0L
    if (digitsHour.isDigitsOnly()) {
        millis += digitsHour.toInt() * MILLIS_PER_HOUR
    }
    if (digitsMinute.isDigitsOnly()) {
        millis += digitsMinute.toInt() * MILLIS_PER_MINUTE
    }
    if (digitsSecond.isDigitsOnly()) {
        millis += digitsSecond.toInt() * MILLIS_PER_SECOND
    }
    return millis
}
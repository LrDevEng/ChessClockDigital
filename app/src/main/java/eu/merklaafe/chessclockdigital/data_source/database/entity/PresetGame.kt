package eu.merklaafe.chessclockdigital.data_source.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import eu.merklaafe.chessclockdigital.model.IncrementType
import eu.merklaafe.chessclockdigital.model.PresetGameText
import eu.merklaafe.chessclockdigital.util.Constants.CHESS_GAME_TABLE
import eu.merklaafe.chessclockdigital.util.parseMillisToDigits

@Entity(tableName = CHESS_GAME_TABLE)
data class PresetGame (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    // Standard game
    val standardTimePlayer1: Long,
    val standardTimePlayer2: Long = standardTimePlayer1,
    val standardIncrementTypePlayer1: IncrementType = IncrementType.None,
    val standardIncrementTypePlayer2: IncrementType = standardIncrementTypePlayer1,
    val standardIncrementPlayer1: Long = 0,
    val standardIncrementPlayer2: Long = standardIncrementPlayer1,

    // First add on
    val firstAddOnMovePlayer1: Int? = null,
    val firstAddOnMovePlayer2: Int? = firstAddOnMovePlayer1,
    val firstAddOnTimePlayer1: Long = 0,
    val firstAddOnTimePlayer2: Long = firstAddOnTimePlayer1,
    val firstAddOnIncrementTypePlayer1: IncrementType = IncrementType.None,
    val firstAddOnIncrementTypePlayer2: IncrementType = firstAddOnIncrementTypePlayer1,
    val firstAddOnIncrementPlayer1: Long = 0,
    val firstAddOnIncrementPlayer2: Long = firstAddOnIncrementPlayer1,

    // Second add on
    val secondAddOnMovePlayer1: Int? = null,
    val secondAddOnMovePlayer2: Int? = secondAddOnMovePlayer1,
    val secondAddOnTimePlayer1: Long = 0,
    val secondAddOnTimePlayer2: Long = secondAddOnTimePlayer1,
    val secondAddOnIncrementTypePlayer1: IncrementType = IncrementType.None,
    val secondAddOnIncrementTypePlayer2: IncrementType = secondAddOnIncrementTypePlayer1,
    val secondAddOnIncrementPlayer1: Long = 0,
    val secondAddOnIncrementPlayer2: Long = secondAddOnIncrementPlayer1
) {
    fun deviatingSettings(): Boolean {
        return standardTimePlayer1 != standardTimePlayer2 ||
               standardIncrementTypePlayer1 != standardIncrementTypePlayer2 ||
               standardIncrementPlayer1 != standardIncrementPlayer2 ||
               firstAddOnMovePlayer1 != firstAddOnMovePlayer2 ||
               firstAddOnTimePlayer1 != firstAddOnTimePlayer2 ||
               firstAddOnIncrementTypePlayer1 != firstAddOnIncrementTypePlayer2 ||
               firstAddOnIncrementPlayer1 != firstAddOnIncrementPlayer2 ||
               secondAddOnMovePlayer1 != secondAddOnMovePlayer2 ||
               secondAddOnTimePlayer1 != secondAddOnTimePlayer2 ||
               secondAddOnIncrementTypePlayer1 != secondAddOnIncrementTypePlayer2 ||
               secondAddOnIncrementPlayer1 != secondAddOnIncrementPlayer2
    }

    fun identicalTo(presetGame: PresetGame): Boolean {
        return standardTimePlayer1 == presetGame.standardTimePlayer1 &&
                standardTimePlayer2 == presetGame.standardTimePlayer2 &&
                standardIncrementTypePlayer1 == presetGame.standardIncrementTypePlayer1 &&
                standardIncrementTypePlayer2 == presetGame.standardIncrementTypePlayer2 &&
                standardIncrementPlayer1 == presetGame.standardIncrementPlayer1 &&
                standardIncrementPlayer2 == presetGame.standardIncrementPlayer2 &&
                firstAddOnMovePlayer1 == presetGame.firstAddOnMovePlayer1 &&
                firstAddOnMovePlayer2 == presetGame.firstAddOnMovePlayer2 &&
                firstAddOnTimePlayer1 == presetGame.firstAddOnTimePlayer1 &&
                firstAddOnTimePlayer2 == presetGame.firstAddOnTimePlayer2 &&
                firstAddOnIncrementTypePlayer1 == presetGame.firstAddOnIncrementTypePlayer1 &&
                firstAddOnIncrementTypePlayer2 == presetGame.firstAddOnIncrementTypePlayer2 &&
                firstAddOnIncrementPlayer1 == presetGame.firstAddOnIncrementPlayer1 &&
                firstAddOnIncrementPlayer2 == presetGame.firstAddOnIncrementPlayer2 &&
                secondAddOnMovePlayer1 == presetGame.secondAddOnMovePlayer1 &&
                secondAddOnMovePlayer2 == presetGame.secondAddOnMovePlayer2 &&
                secondAddOnTimePlayer1 == presetGame.secondAddOnTimePlayer1 &&
                secondAddOnTimePlayer2 == presetGame.secondAddOnTimePlayer2 &&
                secondAddOnIncrementTypePlayer1 == presetGame.secondAddOnIncrementTypePlayer1 &&
                secondAddOnIncrementTypePlayer2 == presetGame.secondAddOnIncrementTypePlayer2 &&
                secondAddOnIncrementPlayer1 == presetGame.secondAddOnIncrementPlayer1 &&
                secondAddOnIncrementPlayer2 == presetGame.secondAddOnIncrementPlayer2
    }

//    fun toText(): PresetGameText {
//        var standardInfoPlayer1 = parseMillisToDigits(
//            millis = standardTimePlayer1,
//            clipLeadingZeros = true
//        )
//        var firstAddOnInfoPlayer1 = ""
//        var secondAddOnInfoPlayer1 = ""
//
//        if (standardIncrementTypePlayer1 != IncrementType.None) {
//            val inc1 = parseMillisToDigits(
//                millis = standardIncrementPlayer1,
//                clipLeadingZeros = true
//            )
//            standardInfoPlayer1 += " ↑ $inc1 $standardIncrementTypePlayer1"
//        }
//
//
//        if (firstAddOnMovePlayer1 != null) {
//            val firstAddOn1 = parseMillisToDigits(
//                millis = firstAddOnTimePlayer1,
//                clipLeadingZeros = true
//            )
//            firstAddOnInfoPlayer1 += "+ $firstAddOn1"
//            if (firstAddOnIncrementTypePlayer1 != IncrementType.None) {
//                val firstIncAddOn1 = parseMillisToDigits(
//                    millis = firstAddOnIncrementPlayer1,
//                    clipLeadingZeros = true
//                )
//                firstAddOnInfoPlayer1 += " ↑ $firstIncAddOn1 $firstAddOnIncrementTypePlayer1"
//            }
//            firstAddOnInfoPlayer1 += " @$firstAddOnMovePlayer1 Moves"
//
//            if (secondAddOnMovePlayer1 != null) {
//                val secondAddOn1 = parseMillisToDigits(
//                    millis = secondAddOnTimePlayer1,
//                    clipLeadingZeros = true
//                )
//                secondAddOnInfoPlayer1 += "+ $secondAddOn1"
//                if (secondAddOnIncrementTypePlayer1 != IncrementType.None) {
//                    val secondIncAddOn1 = parseMillisToDigits(
//                        millis = secondAddOnIncrementPlayer1,
//                        clipLeadingZeros = true
//                    )
//                    secondAddOnInfoPlayer1 += " ↑ $secondIncAddOn1 $secondAddOnIncrementTypePlayer1"
//                }
//                secondAddOnInfoPlayer1 += " @${secondAddOnMovePlayer1 + firstAddOnMovePlayer1} Moves"
//            }
//        }
//
//        var standardInfoPlayer2 = parseMillisToDigits(
//            millis = standardTimePlayer2,
//            clipLeadingZeros = true
//        )
//        var firstAddOnInfoPlayer2 = ""
//        var secondAddOnInfoPlayer2 = ""
//
//        if (standardIncrementTypePlayer2 != IncrementType.None) {
//            val inc2 = parseMillisToDigits(
//                millis = standardIncrementPlayer2,
//                clipLeadingZeros = true
//            )
//            standardInfoPlayer2 += " ↑ $inc2 $standardIncrementTypePlayer2"
//        }
//
//
//        if (firstAddOnMovePlayer2 != null) {
//            val firstAddOn2 = parseMillisToDigits(
//                millis = firstAddOnTimePlayer2,
//                clipLeadingZeros = true
//            )
//            firstAddOnInfoPlayer2 += "+ $firstAddOn2"
//            if (firstAddOnIncrementTypePlayer2 != IncrementType.None) {
//                val firstIncAddOn2 = parseMillisToDigits(
//                    millis = firstAddOnIncrementPlayer2,
//                    clipLeadingZeros = true
//                )
//                firstAddOnInfoPlayer2 += " ↑ $firstIncAddOn2 $firstAddOnIncrementTypePlayer2"
//            }
//            firstAddOnInfoPlayer2 += " @$firstAddOnMovePlayer2 Moves"
//
//            if (secondAddOnMovePlayer2 != null) {
//                val secondAddOn2 = parseMillisToDigits(
//                    millis = secondAddOnTimePlayer2,
//                    clipLeadingZeros = true
//                )
//                secondAddOnInfoPlayer2 += "+ $secondAddOn2"
//                if (secondAddOnIncrementTypePlayer2 != IncrementType.None) {
//                    val secondIncAddOn2 = parseMillisToDigits(
//                        millis = secondAddOnIncrementPlayer2,
//                        clipLeadingZeros = true
//                    )
//                    secondAddOnInfoPlayer2 += " ↑ $secondIncAddOn2 $secondAddOnIncrementTypePlayer2"
//                }
//                secondAddOnInfoPlayer2 += " @${secondAddOnMovePlayer2 + firstAddOnMovePlayer2} Moves"
//            }
//        }
//
//        return PresetGameText(
//            standardInfoPlayer1 = standardInfoPlayer1,
//            firstAddOnInfoPlayer1 = firstAddOnInfoPlayer1,
//            secondAddOnInfoPlayer1 = secondAddOnInfoPlayer1,
//            standardInfoPlayer2 = standardInfoPlayer2,
//            firstAddOnInfoPlayer2 = firstAddOnInfoPlayer2,
//            secondAddOnInfoPlayer2 = secondAddOnInfoPlayer2
//        )
//    }
}
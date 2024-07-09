package eu.merklaafe.chessclockdigital.model

import androidx.room.TypeConverter

enum class IncrementType(
    val shortName: String
) {
    None(
        shortName = "None"
    ),

    Fischer(
        shortName = "Fisch"
    ),

    Bronstein(
        shortName = "Bron"
    ),
}

class IncrementTypeConverter {
    @TypeConverter
    fun incrementTypeFromDb(ordinal: Int): IncrementType {
        return IncrementType.entries[ordinal]
    }

    @TypeConverter
    fun incrementTypeToDb(enum: IncrementType): Int {
        return enum.ordinal
    }
}
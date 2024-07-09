package eu.merklaafe.chessclockdigital.data_source.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import eu.merklaafe.chessclockdigital.data_source.database.entity.PresetGame
import eu.merklaafe.chessclockdigital.model.IncrementTypeConverter

@Database(
    entities = [PresetGame::class],
    version = 1
)
@TypeConverters(IncrementTypeConverter::class)
abstract class PresetChessGameDatabase: RoomDatabase() {
    abstract fun chessGameDao(): PresetChessGameDao
}
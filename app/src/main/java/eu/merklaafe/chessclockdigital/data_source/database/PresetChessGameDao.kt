package eu.merklaafe.chessclockdigital.data_source.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import eu.merklaafe.chessclockdigital.data_source.database.entity.PresetGame
import kotlinx.coroutines.flow.Flow
import java.sql.RowId

@Dao
interface PresetChessGameDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addChessGame(presetGame: PresetGame): Long

    @Delete
    suspend fun deleteChessGame(presetGame: PresetGame)

    @Query("DELETE FROM chess_game_table WHERE id=:gameId")
    suspend fun deleteChessGame(gameId: Int)

    @Query("SELECT * FROM chess_game_table ORDER BY " +
            "standardTimePlayer1 ASC, standardIncrementTypePlayer1 ASC, standardIncrementPlayer1 ASC," +
            "firstAddOnMovePlayer1 ASC, firstAddOnTimePlayer1 ASC, firstAddOnIncrementTypePlayer1 ASC, firstAddOnIncrementPlayer1 ASC," +
            "secondAddOnMovePlayer1 ASC, secondAddOnTimePlayer1 ASC, secondAddOnIncrementTypePlayer1 ASC, secondAddOnIncrementPlayer1 ASC")
    fun getAllChessGames(): Flow<List<PresetGame>>

    @Query("SELECT * FROM chess_game_table ORDER BY " +
            "standardTimePlayer1 ASC, standardIncrementTypePlayer1 ASC, standardIncrementPlayer1 ASC," +
            "firstAddOnMovePlayer1 ASC, firstAddOnTimePlayer1 ASC, firstAddOnIncrementTypePlayer1 ASC, firstAddOnIncrementPlayer1 ASC," +
            "secondAddOnMovePlayer1 ASC, secondAddOnTimePlayer1 ASC, secondAddOnIncrementTypePlayer1 ASC, secondAddOnIncrementPlayer1 ASC")
    suspend fun getAllChessGamesOnce(): List<PresetGame>

    @Query("SELECT * FROM chess_game_table WHERE id=:gameId LIMIT 1")
    suspend fun getSelectedGame(gameId: Int): PresetGame

    @Query("SELECT * FROM chess_game_table WHERE rowid=:rowId LIMIT 1")
    suspend fun getGame(rowId: Long): PresetGame
}
package eu.merklaafe.chessclockdigital.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import eu.merklaafe.chessclockdigital.data_source.cache.CurrentGame
import eu.merklaafe.chessclockdigital.data_source.database.PresetChessGameDao
import eu.merklaafe.chessclockdigital.data_source.database.PresetChessGameDatabase
import eu.merklaafe.chessclockdigital.data_source.database.entity.PresetGame
import eu.merklaafe.chessclockdigital.data_source.repository.ChessClockRepositoryImpl
import eu.merklaafe.chessclockdigital.data_source.repository.PreferencesRepositoryImpl
import eu.merklaafe.chessclockdigital.data_source.repository.PresetGamesRepositoryImpl
import eu.merklaafe.chessclockdigital.presentation.repository.ChessClockRepository
import eu.merklaafe.chessclockdigital.presentation.repository.PreferencesRepository
import eu.merklaafe.chessclockdigital.presentation.repository.PresetGamesRepository
import eu.merklaafe.chessclockdigital.util.AdvancedCountDownTimer
import eu.merklaafe.chessclockdigital.util.Constants
import eu.merklaafe.chessclockdigital.util.Constants.DATA_STORE_PREFERENCES_NAME
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ChessClockModule {
    @Provides
    @Singleton
    fun provideChessGameDatabase(
        @ApplicationContext context: Context
    ): PresetChessGameDatabase {
        return Room.databaseBuilder(
            context = context.applicationContext,
            klass = PresetChessGameDatabase::class.java,
            name = Constants.CHESS_GAME_DATABASE
        ).createFromAsset("database/InitialPresetGames.db").build()
    }

    @Provides
    @Singleton
    fun provideChessGameDao(database: PresetChessGameDatabase) = database.chessGameDao()

    @Provides
    fun providePresetGame() = PresetGame(standardTimePlayer1 = 300000, standardIncrementPlayer1 = 3000)

    @Provides
    fun provideAdvancedCountDownTimer(presetGame: PresetGame) = AdvancedCountDownTimer(presetGame.standardTimePlayer1)

    @Provides
    @Singleton
    fun provideCurrentGame(
        presetGame: PresetGame,
        advancedCountDownTimer1: AdvancedCountDownTimer,
        advancedCountDownTimer2: AdvancedCountDownTimer
    ) = CurrentGame(
        presetGame = presetGame,
        advancedCountDownTimer1 = advancedCountDownTimer1,
        advancedCountDownTimer2 = advancedCountDownTimer2
    )

    @Provides
    @Singleton
    fun provideDataStorePreferences(@ApplicationContext context: Context): DataStore<Preferences> = PreferenceDataStoreFactory
        .create(
            corruptionHandler = ReplaceFileCorruptionHandler(
                produceNewData = { emptyPreferences() }
            ),
            produceFile = {context.preferencesDataStoreFile(DATA_STORE_PREFERENCES_NAME)}
        )

    @Provides
    @Singleton
    fun provideChessClockRepository(
        presetChessGameDao: PresetChessGameDao,
        currentGame: CurrentGame,
        preferences: DataStore<Preferences>
    ): ChessClockRepository = ChessClockRepositoryImpl(
        presetChessGameDao = presetChessGameDao,
        currentGame = currentGame,
        preferences = preferences
    )

    @Provides
    @Singleton
    fun providePresetGamesRepository(
        presetChessGameDao: PresetChessGameDao
    ): PresetGamesRepository = PresetGamesRepositoryImpl(presetChessGameDao)

    @Provides
    @Singleton
    fun providePreferencesRepository(
        preferences: DataStore<Preferences>
    ): PreferencesRepository = PreferencesRepositoryImpl(
        preferences = preferences
    )
}
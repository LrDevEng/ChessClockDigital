package eu.merklaafe.chessclockdigital.data_source.cache

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import eu.merklaafe.chessclockdigital.data_source.database.entity.PresetGame
import eu.merklaafe.chessclockdigital.model.GamePeriod
import eu.merklaafe.chessclockdigital.model.GameState
import eu.merklaafe.chessclockdigital.model.GameState.*
import eu.merklaafe.chessclockdigital.model.IncrementType.*
import eu.merklaafe.chessclockdigital.model.Player
import eu.merklaafe.chessclockdigital.model.Player.*
import eu.merklaafe.chessclockdigital.util.AdvancedCountDownTimer
import eu.merklaafe.chessclockdigital.util.Constants.MILLIS_PER_SECOND
import eu.merklaafe.chessclockdigital.util.CountDownListener
import javax.inject.Inject

// Class containing logic of the chess clock
class CurrentGame @Inject constructor (
    var presetGame: PresetGame,
    private val advancedCountDownTimer1: AdvancedCountDownTimer,
    private val advancedCountDownTimer2: AdvancedCountDownTimer
) {
    // --- General game data -----------------------------------------------------------------------
    private var _gameState: GameState = Idle
    private var _playersTurn: Player = Player1

    // --- Player 1 --------------------------------------------------------------------------------
    // General data player 1
    private var _timer1 = presetGame.standardTimePlayer1
    private var _movesPlayer1 = 0
    private var _gamePeriodPlayer1 = GamePeriod.Standard
    private var _incrementPlayer1 = presetGame.standardIncrementPlayer1
    private var _incrementTypePlayer1 = presetGame.standardIncrementTypePlayer1
    private var _startTimePlayer1 = 0L

    // --- Player 2 --------------------------------------------------------------------------------
    // General data player 2
    private var _timer2 = presetGame.standardTimePlayer2
    private var _movesPlayer2 = 0
    private var _gamePeriodPlayer2 = GamePeriod.Standard
    private var _incrementPlayer2 = presetGame.standardIncrementPlayer2
    private var _incrementTypePlayer2 = presetGame.standardIncrementTypePlayer2
    private var _startTimePlayer2 = 0L

    // --- Game observers --------------------------------------------------------------------------
    private val observers: MutableList<CurrentGameObserver> = emptyList<CurrentGameObserver>().toMutableList()

    init {
        // Register listeners to count down timers
        advancedCountDownTimer1.subscribe(
            object: CountDownListener {
                override fun onTimerUpdated(time: Long) {
                    if (_timer1/MILLIS_PER_SECOND != time/MILLIS_PER_SECOND) {
                        _timer1 = time
                        if (time < 999L) {
                            _gameState = Finished
                            Log.d("CurrentGame","Game finished timer 1")
                        }
                        notifyObservers()
                    }
                }
            }
        )

        advancedCountDownTimer2.subscribe(
            object: CountDownListener {
                override fun onTimerUpdated(time: Long) {
                    if (_timer2/MILLIS_PER_SECOND != time/MILLIS_PER_SECOND) {
                        _timer2 = time
                        if (time < 999L) {
                            _gameState = Finished
                            Log.d("CurrentGame","Game finished timer 2")
                        }
                        notifyObservers()
                    }
                }
            }
        )
    }

    // --- Logic functions (public) ----------------------------------------------------------------
    fun inputPlayer1() {
        when(_gameState) {
            Idle -> {
                startTimer2()
                _playersTurn = Player2
                _gameState = Running
            }
            Running -> {
                if (_playersTurn is Player1) {
                    pauseTimer1(increaseMoves = true)
                    startTimer2()
                    _playersTurn = Player2
                }
            }
            Paused -> {
                when(_playersTurn) {
                    Player1 -> {startTimer1()}
                    Player2 -> {startTimer2()}
                }
                _gameState = Running
            }
            Finished -> {
                return
            }
        }
        notifyObservers()
    }



    fun inputPlayer2() {
        when(_gameState) {
            Idle -> {
                startTimer1()
                _playersTurn = Player1
                _gameState = Running
            }
            Running -> {
                if (_playersTurn is Player2) {
                    pauseTimer2(increaseMoves = true)
                    startTimer1()
                    _playersTurn = Player1
                }
            }
            Paused -> {
                when(_playersTurn) {
                    Player1 -> {startTimer1()}
                    Player2 -> {startTimer2()}
                }
                _gameState = Running
            }
            Finished -> {
                return
            }
        }
        notifyObservers()
    }

    fun inputPlayPause() {
        when(_gameState) {
            Idle -> {
                startTimer1()
                _playersTurn = Player1
                _gameState = Running
            }
            Running -> {
                _gameState = Paused
                when(_playersTurn) {
                    Player1 -> {pauseTimer1(increaseMoves = false)}
                    Player2 -> {pauseTimer2(increaseMoves = false)}
                }
            }
            Paused -> {
                when(_playersTurn) {
                    Player1 -> {startTimer1()}
                    Player2 -> {startTimer2()}
                }
                _gameState = Running
            }
            Finished -> {
                return
            }
        }
        notifyObservers()
    }

    fun reset(
        newGame: PresetGame? = presetGame
    ) {
        if (newGame != null) {
            presetGame = newGame
        }
        loadGame()
        notifyObservers()
    }

    // --- Handle observers/listeners --------------------------------------------------------------
    fun subscribe(observer: CurrentGameObserver) {
        observers.add(observer)
    }

    fun unsubscribe(observer: CurrentGameObserver) {
        observers.remove(observer)
    }

    private fun notifyObservers() {
        val currentGameState = CurrentGameState(
            gameState = _gameState,
            playersTurn = _playersTurn,
            timer1 = _timer1,
            movesPlayer1 = _movesPlayer1,
            gamePeriodPlayer1 = _gamePeriodPlayer1,
            incrementPlayer1 = _incrementPlayer1,
            incrementTypePlayer1 = _incrementTypePlayer1,
            startTimePlayer1 = _startTimePlayer1,
            timer2 = _timer2,
            movesPlayer2 = _movesPlayer2,
            gamePeriodPlayer2 = _gamePeriodPlayer2,
            incrementPlayer2 = _incrementPlayer2,
            incrementTypePlayer2 = _incrementTypePlayer2,
            startTimePlayer2 = _startTimePlayer2
        )
        observers.forEach {
            it.onGameUpdated(
                currentGameState
            )
        }
        Log.d("CurrentGame","Notified observes about game state update.")
    }

    // --- Private functions -----------------------------------------------------------------------
    private fun startTimer1() {
        advancedCountDownTimer1.start()?.let {
            if (_gameState !is Paused)
                _startTimePlayer1 = it
        }
    }

    private fun startTimer2() {
        advancedCountDownTimer2.start()?.let {
            if (_gameState !is Paused)
                _startTimePlayer2 = it
        }
    }

    private fun pauseTimer1(increaseMoves: Boolean) {
        // Pause timer and get current time
        val (pauseTime, wasRunning) = advancedCountDownTimer1.pause()

        // Return if timer was not running or game was already paused in general
        if (wasRunning.not() || _gameState is Paused)
            return

        // Increase moves
        if (increaseMoves)
            _movesPlayer1 += 1

        // Update game state and calculate add on time
        val addOnTime = updateGameState(Player1)

        // Adjust add on time based on increment/delay
        when(_incrementTypePlayer1) {
            Fischer -> {
                advancedCountDownTimer1.pauseAndAdd(_incrementPlayer1 + addOnTime)
            }
            Bronstein -> {
                if (pauseTime + _incrementPlayer1 > _startTimePlayer1)
                    advancedCountDownTimer1.pauseAndSet(_startTimePlayer1 + addOnTime)
                else
                    advancedCountDownTimer1.pauseAndAdd(_incrementPlayer1 + addOnTime)
            }
            None -> {
                advancedCountDownTimer1.pauseAndAdd(addOnTime)
            }
        }
    }

    private fun pauseTimer2(increaseMoves: Boolean) {
        // Pause timer and get current time
        val (pauseTime, wasRunning) = advancedCountDownTimer2.pause()

        // Return if timer was not running or game was already paused in general
        if (wasRunning.not() || _gameState is Paused)
            return

        // Increase moves
        if (increaseMoves)
            _movesPlayer2 += 1

        // Update game state and calculate add on time
        val addOnTime = updateGameState(Player2)

        // Adjust add on time based on increment/delay
        when(_incrementTypePlayer2) {
            Fischer -> {
                advancedCountDownTimer2.pauseAndAdd(_incrementPlayer2 + addOnTime)
            }
            Bronstein -> {
                if (pauseTime + _incrementPlayer2 > _startTimePlayer2)
                    advancedCountDownTimer2.pauseAndSet(_startTimePlayer2 + addOnTime)
                else
                    advancedCountDownTimer2.pauseAndAdd(_incrementPlayer2 + addOnTime)
            }
            None -> {
                advancedCountDownTimer2.pauseAndAdd(addOnTime)
            }
        }
    }

    // Update game state based on move number and return optional add on time
    private fun updateGameState(player: Player): Long {
        when(player) {
            Player1 -> {
                if (presetGame.firstAddOnMovePlayer1 == null) {
                    return 0
                }
                if (_movesPlayer1 == presetGame.firstAddOnMovePlayer1) {
                    _gamePeriodPlayer1 = GamePeriod.FirstAddOn
                    _incrementTypePlayer1 = presetGame.firstAddOnIncrementTypePlayer1
                    _incrementPlayer1 = calculateIncrement(player)
                    return presetGame.firstAddOnTimePlayer1
                }
                if (presetGame.secondAddOnMovePlayer1 == null) {
                    return 0
                }
                if (_movesPlayer1 == (presetGame.firstAddOnMovePlayer1!! + presetGame.secondAddOnMovePlayer1!!)) {
                    _gamePeriodPlayer1 = GamePeriod.SecondAddOn
                    _incrementTypePlayer1 = presetGame.secondAddOnIncrementTypePlayer1
                    _incrementPlayer1 = calculateIncrement(player)
                    return presetGame.secondAddOnTimePlayer1
                }
            }
            Player2 -> {
                if (presetGame.firstAddOnMovePlayer2 == null) {
                    return 0
                }
                if (_movesPlayer2 == presetGame.firstAddOnMovePlayer2) {
                    _gamePeriodPlayer2 = GamePeriod.FirstAddOn
                    _incrementTypePlayer2 = presetGame.firstAddOnIncrementTypePlayer2
                    _incrementPlayer2 = calculateIncrement(player)
                    return presetGame.firstAddOnTimePlayer2
                }
                if (presetGame.secondAddOnMovePlayer2 == null) {
                    return 0
                }
                if (_movesPlayer2 == (presetGame.firstAddOnMovePlayer2!! + presetGame.secondAddOnMovePlayer2!!)) {
                    _gamePeriodPlayer2 = GamePeriod.SecondAddOn
                    _incrementTypePlayer2 = presetGame.secondAddOnIncrementTypePlayer2
                    _incrementPlayer2 = calculateIncrement(player)
                    return presetGame.secondAddOnTimePlayer2
                }
            }
        }
        return 0
    }

    // Calculate increment based on game state
    private fun calculateIncrement(player: Player): Long {
        when(player) {
            Player1 -> {
                when(_gamePeriodPlayer1) {
                    GamePeriod.Standard -> {
                        return if (presetGame.standardIncrementTypePlayer1 == None)
                            0
                        else
                            presetGame.standardIncrementPlayer1
                    }
                    GamePeriod.FirstAddOn -> {
                        return if (presetGame.firstAddOnIncrementTypePlayer1 == None)
                            0
                        else
                            presetGame.firstAddOnIncrementPlayer1
                    }
                    GamePeriod.SecondAddOn -> {
                        return if (presetGame.secondAddOnIncrementTypePlayer1 == None)
                            0
                        else
                            presetGame.secondAddOnIncrementPlayer1
                    }
                }
            }
            Player2 -> {
                when(_gamePeriodPlayer2) {
                    GamePeriod.Standard -> {
                        return if (presetGame.standardIncrementTypePlayer2 == None)
                            0
                        else
                            presetGame.standardIncrementPlayer2
                    }
                    GamePeriod.FirstAddOn -> {
                        return if (presetGame.firstAddOnIncrementTypePlayer2 == None)
                            0
                        else
                            presetGame.firstAddOnIncrementPlayer2
                    }
                    GamePeriod.SecondAddOn -> {
                        return if (presetGame.secondAddOnIncrementTypePlayer2 == None)
                            0
                        else
                            presetGame.secondAddOnIncrementPlayer2
                    }
                }
            }
        }
    }

    // Load game
    private fun loadGame() {
        _gameState = Idle
        _playersTurn = Player1

        advancedCountDownTimer1.reset(
            presetGame.standardTimePlayer1,
        )
        _timer1 = presetGame.standardTimePlayer1
        _movesPlayer1 = 0
        _gamePeriodPlayer1 = GamePeriod.Standard
        _incrementPlayer1 = presetGame.standardIncrementPlayer1
        _incrementTypePlayer1 = presetGame.standardIncrementTypePlayer1

        advancedCountDownTimer2.reset(
            presetGame.standardTimePlayer2,
        )
        _timer2 = presetGame.standardTimePlayer2
        _movesPlayer2 = 0
        _gamePeriodPlayer2 = GamePeriod.Standard
        _incrementPlayer2 = presetGame.standardIncrementPlayer2
        _incrementTypePlayer2 = presetGame.standardIncrementTypePlayer2
    }
}
package eu.merklaafe.chessclockdigital.util

import android.os.CountDownTimer
import android.util.Log
import eu.merklaafe.chessclockdigital.util.Constants.TENTH_OF_A_SECOND_IN_MILLIS

class AdvancedCountDownTimer(
    private val millisInFuture: Long,
    private val countDownInterval: Long = TENTH_OF_A_SECOND_IN_MILLIS,
) {
    private val listeners: MutableList<CountDownListener> = emptyList<CountDownListener>().toMutableList()
    private var _millisInFuture = millisInFuture
    private var _countDownInterval = countDownInterval
    private var simpleCountDownTimer:SimpleCountDownTimer? = null

    fun start(): Long? {
        if (simpleCountDownTimer == null) {
            simpleCountDownTimer = SimpleCountDownTimer(
                _millisInFuture,
                _countDownInterval,
                onTickDown = {
                    _millisInFuture = it
                    notifySubscribers()
                },
                onFinished = {
                    _millisInFuture = 0
                    notifySubscribers()
                }
            )
            simpleCountDownTimer!!.start()
            return _millisInFuture
            // Log.d("AdvancedCountDownTimer","New count down started.")
        }
        return null
    }

    fun pause(): Pair<Long, Boolean> {
        if (simpleCountDownTimer == null) {
            return _millisInFuture to false
        }
        simpleCountDownTimer?.cancel()
        simpleCountDownTimer = null
        notifySubscribers()
        return _millisInFuture to true
    }

    fun pauseAndAdd(addOnTime: Long): Pair<Long, Boolean> {
        val (pauseTime, wasRunning) = pause()
        _millisInFuture += addOnTime
        notifySubscribers()
        return pauseTime to wasRunning
    }

    fun pauseAndSet(setTime: Long): Pair<Long, Boolean> {
        val (_, wasRunning) = pause()
        _millisInFuture = setTime
        notifySubscribers()
        return _millisInFuture to wasRunning
    }

    fun reset(
        millis: Long = millisInFuture,
        interval: Long = countDownInterval
    ) {
        simpleCountDownTimer?.cancel()
        simpleCountDownTimer = null
        _millisInFuture = millis
        _countDownInterval = interval
        notifySubscribers()
    }


    // --- Handle observers/listeners --------------------------------------------------------------
    fun subscribe(listener: CountDownListener) {
        listeners.add(listener)
    }

    fun unsubscribe(listener: CountDownListener) {
        listeners.remove(listener)
    }

    private fun notifySubscribers() {
        listeners.forEach {
            it.onTimerUpdated(_millisInFuture)
        }
    }
}

// Implementation of abstract class "android.os.CountDownTimer"
private class SimpleCountDownTimer(
    millisInFuture: Long,
    countDownInterval: Long,
    val onTickDown: (Long) -> Unit,
    val onFinished: () -> Unit
):CountDownTimer(millisInFuture,countDownInterval) {
    override fun onTick(millisUntilFinished: Long) {
        onTickDown(millisUntilFinished)
    }

    override fun onFinish() {
        onFinished()
    }
}
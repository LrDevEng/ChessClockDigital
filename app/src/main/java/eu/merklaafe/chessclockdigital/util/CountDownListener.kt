package eu.merklaafe.chessclockdigital.util

interface CountDownListener {
    fun onTimerUpdated(time: Long): Unit
}
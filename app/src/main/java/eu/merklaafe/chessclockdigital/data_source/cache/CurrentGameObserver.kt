package eu.merklaafe.chessclockdigital.data_source.cache

interface CurrentGameObserver {
    fun onGameUpdated(currentGameState: CurrentGameState)
}
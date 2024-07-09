package eu.merklaafe.chessclockdigital.model

sealed class Player {
    data object Player1: Player()
    data object Player2: Player()
}
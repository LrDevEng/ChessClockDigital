package eu.merklaafe.chessclockdigital.model

sealed class TimeConfigurationStatus {
    data object SavedSuccessfully: TimeConfigurationStatus()
    data object SavingError: TimeConfigurationStatus()

    data object StandardTimeIssue: TimeConfigurationStatus()
    data object StandardIncrementIssue: TimeConfigurationStatus()

    data object FirstAddOnMoveIssue: TimeConfigurationStatus()
    data object FirstAddOnTimeIssue: TimeConfigurationStatus()
    data object FirstAddOnIncrementIssue: TimeConfigurationStatus()

    data object SecondAddOnMoveIssue: TimeConfigurationStatus()
    data object SecondAddOnTimeIssue: TimeConfigurationStatus()
    data object SecondAddOnIncrementIssue: TimeConfigurationStatus()
}
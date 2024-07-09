package eu.merklaafe.chessclockdigital.navigation

sealed class Screen(val route:String) {
    data object Home: Screen(route = "home_screen")
    data object TimeSelection: Screen(route = "time_selection_screen")
    data object TimeConfiguration: Screen(route = "time_configuration_screen")
    data object Settings: Screen(route = "settings_screen")
}
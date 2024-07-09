package eu.merklaafe.chessclockdigital

import android.os.Bundle
import android.view.MotionEvent
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import eu.merklaafe.chessclockdigital.model.ThemeConfig
import eu.merklaafe.chessclockdigital.navigation.NavigationGraph
import eu.merklaafe.chessclockdigital.navigation.Screen
import eu.merklaafe.chessclockdigital.presentation.repository.PreferencesRepository
import eu.merklaafe.chessclockdigital.ui.theme.ChessClockDigitalTheme
import eu.merklaafe.chessclockdigital.util.Constants
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Hide system bars
        val windowInsetsController = WindowCompat.getInsetsController(window, window.decorView)
        windowInsetsController.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())

        setContent {
            var themeConfig by rememberSaveable { mutableStateOf(ThemeConfig.System)}
            ChessClockDigitalTheme(
                themeConfig = themeConfig
            ) {
                val navHostController = rememberNavController()
                NavigationGraph(
                    startDestination = Screen.Home.route,
                    navHostController = navHostController,
                    systemBarVisible = {show ->
                        if (show)
                            windowInsetsController.show(WindowInsetsCompat.Type.systemBars())
                        else
                            windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())
                    },
                    keepScreenOn = {keepOn ->
                        if (keepOn) {
                            window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
                        } else {
                            window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
                        }
                    },
                    themeConfigUpdate = {
                        themeConfig = it
                    }
                )
            }
        }
    }
}
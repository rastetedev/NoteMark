package com.raulastete.notemark.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalConfiguration
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.raulastete.notemark.presentation.designsystem.core.NoteMarkTheme
import com.raulastete.notemark.presentation.navigation.AppGraph
import com.raulastete.notemark.presentation.utils.DeviceModeManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainActivity : ComponentActivity() {

    private val _startDestinationIsReady = MutableStateFlow(false)
    val startDestinationIsReady = _startDestinationIsReady.asStateFlow()

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                startDestinationIsReady.value.not()
            }
        }
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            NoteMarkTheme {

                val widthSizeClass = calculateWindowSizeClass(this).widthSizeClass
                val heightSizeClass = calculateWindowSizeClass(this).heightSizeClass
                val configuration = LocalConfiguration.current

                val deviceModeManager = remember {
                    DeviceModeManager(
                        widthSizeClass,
                        heightSizeClass,
                        configuration
                    )
                }

                AppGraph(
                    deviceMode = deviceModeManager.getDeviceMode(),
                    startDestinationIsReady = {
                        _startDestinationIsReady.value = true
                    }
                )
            }
        }
    }
}
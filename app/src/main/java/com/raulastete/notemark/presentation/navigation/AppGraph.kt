package com.raulastete.notemark.presentation.navigation

import android.content.res.Configuration.ORIENTATION_LANDSCAPE
import android.content.res.Configuration.ORIENTATION_PORTRAIT
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.raulastete.notemark.presentation.utils.DeviceMode

@Composable
fun AppGraph(
    windowWidthSizeClass: WindowWidthSizeClass,
    windowHeightSizeClass: WindowHeightSizeClass
) {

    val configuration = LocalConfiguration.current
    val navController = rememberNavController()

    val deviceMode = when {
        windowWidthSizeClass == WindowWidthSizeClass.Compact &&
                configuration.orientation == ORIENTATION_PORTRAIT -> {
            DeviceMode.PhonePortrait
        }

        windowHeightSizeClass == WindowHeightSizeClass.Compact &&
                configuration.orientation == ORIENTATION_LANDSCAPE -> {
            DeviceMode.PhoneLandscape
        }

        configuration.orientation == ORIENTATION_PORTRAIT -> {
            DeviceMode.TabletPortrait
        }

        else -> {
            DeviceMode.TabletLandscape
        }
    }

    NavHost(navController = navController, startDestination = Destination.Landing) {
        composable<Destination.Landing> {
        }

        composable<Destination.Login> {
        }

        composable<Destination.Registration> {
        }
    }
}

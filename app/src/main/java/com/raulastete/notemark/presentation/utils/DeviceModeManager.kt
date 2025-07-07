package com.raulastete.notemark.presentation.utils

import android.content.res.Configuration
import android.content.res.Configuration.ORIENTATION_PORTRAIT
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass

class DeviceModeManager(
    private val widthSizeClass: WindowWidthSizeClass,
    private val heightSizeClass: WindowHeightSizeClass,
    private val configuration: Configuration
) {

    fun getDeviceMode(): DeviceMode {
        return when {
            widthSizeClass == WindowWidthSizeClass.Compact && heightSizeClass == WindowHeightSizeClass.Compact -> {
                if (configuration.orientation == ORIENTATION_PORTRAIT) {
                    DeviceMode.PhonePortrait
                } else {
                    DeviceMode.PhoneLandscape
                }
            }
            widthSizeClass == WindowWidthSizeClass.Medium && heightSizeClass == WindowHeightSizeClass.Compact -> {
                if (configuration.orientation == ORIENTATION_PORTRAIT) {
                    DeviceMode.TabletPortrait
                } else {
                    DeviceMode.TabletLandscape
                }
            }
            else -> DeviceMode.TabletLandscape // Default case for larger screens
        }
    }
}
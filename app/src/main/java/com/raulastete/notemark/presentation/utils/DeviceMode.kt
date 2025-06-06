package com.raulastete.notemark.presentation.utils

sealed interface DeviceMode {

    object PhonePortrait : DeviceMode
    object PhoneLandscape : DeviceMode
    object TabletPortrait : DeviceMode
    object TabletLandscape : DeviceMode
}
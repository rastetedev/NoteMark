package com.raulastete.notemark.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.raulastete.notemark.domain.usecase.IsUserAuthenticatedUseCase
import com.raulastete.notemark.presentation.screens.home.HomeRoot
import com.raulastete.notemark.presentation.screens.landing.LandingRoot
import com.raulastete.notemark.presentation.screens.login.LoginRoot
import com.raulastete.notemark.presentation.screens.note_form.NoteFormRoot
import com.raulastete.notemark.presentation.screens.registration.RegistrationRoot
import com.raulastete.notemark.presentation.screens.settings.SettingsRoot
import com.raulastete.notemark.presentation.utils.DeviceMode
import kotlinx.coroutines.launch
import org.koin.compose.koinInject

@Composable
fun AppGraph(
    deviceMode: DeviceMode,
    isUserAuthenticatedUseCase: IsUserAuthenticatedUseCase = koinInject(),
    startDestinationIsReady: () -> Unit
) {

    val navController = rememberNavController()
    val scope = rememberCoroutineScope()
    var startDestination by remember { mutableStateOf<Destination?>(null) }

    LaunchedEffect(Unit) {
        scope.launch {
            val isAuthenticated = isUserAuthenticatedUseCase.invoke()
            startDestination = if (isAuthenticated) Destination.Home else Destination.Landing
            startDestinationIsReady()
        }
    }

    startDestination?.let { startDestination ->
        NavHost(navController = navController, startDestination = startDestination) {
            composable<Destination.Landing> {
                LandingRoot(
                    deviceMode = deviceMode,
                    navigateToLogin = {
                        navController.navigate(Destination.Login) {
                            popUpTo(Destination.Landing) { inclusive = true }
                        }
                    },
                    navigateToRegistration = {
                        navController.navigate(Destination.Registration) {
                            popUpTo(Destination.Landing) { inclusive = true }
                        }
                    }
                )
            }

            composable<Destination.Login> {
                LoginRoot(
                    deviceMode = deviceMode,
                    navigateToRegistration = {
                        navController.navigate(Destination.Registration) {
                            popUpTo(Destination.Registration) { inclusive = true }
                            launchSingleTop = true
                        }
                    },
                    navigateToHome = {
                        navController.navigate(Destination.Home) {
                            popUpTo(Destination.Login) { inclusive = true }
                        }
                    }
                )
            }

            composable<Destination.Registration> {
                RegistrationRoot(
                    deviceMode = deviceMode,
                    navigateToLogin = {
                        navController.navigate(Destination.Login) {
                            popUpTo(Destination.Login) { inclusive = true }
                            launchSingleTop = true
                        }
                    }
                )
            }

            composable<Destination.Home> {
                HomeRoot(
                    deviceMode = deviceMode,
                    navigateToNoteForm = { noteId ->
                        navController.navigate(Destination.NoteForm(noteId))
                    },
                    navigateToSettings = {
                        navController.navigate(Destination.Settings)
                    }
                )
            }

            composable<Destination.NoteForm> {
                NoteFormRoot(
                    deviceMode = deviceMode,
                    navigateBack = {
                        navController.navigateUp()
                    }
                )
            }

            composable<Destination.Settings> {
                SettingsRoot(
                    navigateBack = {
                        navController.navigateUp()
                    },
                    navigateToLogin = {
                        navController.navigate(Destination.Login) {
                            popUpTo(0) { inclusive = true }
                            launchSingleTop = true
                        }
                    }
                )
            }
        }
    }
}
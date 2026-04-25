package com.vkartik.wordduel.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.vkartik.wordduel.ui.onboarding.OnboardingScreen
import com.vkartik.wordduel.ui.onboarding.SplashScreen
import kotlinx.coroutines.delay

object Routes {
    const val SPLASH = "splash"
    const val ONBOARDING = "onboarding"
}

@Composable
fun WordDuelNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = Routes.SPLASH
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(Routes.SPLASH) {
            SplashScreen()
            
            LaunchedEffect(Unit) {
                delay(2000) // 2-second delay
                navController.navigate(Routes.ONBOARDING) {
                    popUpTo(Routes.SPLASH) { inclusive = true }
                }
            }
        }
        
        composable(Routes.ONBOARDING) {
            OnboardingScreen(
                onFinish = {
                    // Navigate to next screen (e.g. Login or Home) when completed
                }
            )
        }
    }
}

package com.finance.spendlytics

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.fragment.app.FragmentActivity
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.finance.spendlytics.ui.screens.LandingScreen
import com.finance.spendlytics.ui.screens.auth.AuthScreen
import com.finance.spendlytics.ui.screens.splash.SplashScreen
import com.finance.spendlytics.ui.theme.SpendlyticsTheme

class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SpendlyticsTheme {
                SpendlyticsApp()
            }
        }
    }
}

@Composable
fun SpendlyticsApp() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "splash") {
        composable("splash") {
            SplashScreen(navController = navController)
        }
        composable("auth") {
            AuthScreen(navController = navController)
        }
        composable("landing") {
            LandingScreen()
        }
    }
}
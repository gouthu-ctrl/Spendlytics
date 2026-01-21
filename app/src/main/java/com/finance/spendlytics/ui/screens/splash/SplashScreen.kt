package com.finance.spendlytics.ui.screens.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.finance.spendlytics.R
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {
    // This effect will trigger the navigation after a delay
    LaunchedEffect(key1 = true) {
        delay(2000) // Wait for 2 seconds
        navController.navigate("auth") {
            // Remove the splash screen from the back stack
            popUpTo("splash") { inclusive = true }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_spendlytics_logo),
            contentDescription = "Spendlytics Logo",
            modifier = Modifier.size(120.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text("Spendlytics", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
    }
}
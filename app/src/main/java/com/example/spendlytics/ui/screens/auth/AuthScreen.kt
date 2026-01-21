package com.example.spendlytics.ui.screens.auth

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.finance.spendlytics.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException

@Composable
fun AuthScreen(navController: NavController, authViewModel: AuthViewModel = viewModel()) {
    val context = LocalContext.current
    val activity = context as FragmentActivity
    val signInState by authViewModel.signInState.collectAsState()

    // Google Sign-In Launcher
    val googleSignInLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = { result ->
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                val account = task.getResult(ApiException::class.java)
                if (account?.idToken != null) {
                    authViewModel.signInWithGoogle(account.idToken!!)
                }
            } catch (e: ApiException) {
                Toast.makeText(context, "Google Sign-In failed: ${e.statusCode}", Toast.LENGTH_SHORT).show()
            }
        }
    )

    // Observe the sign-in state
    LaunchedEffect(signInState) {
        when (val state = signInState) {
            is SignInState.Success -> {
                if (!state.isNewUser) {
                    navController.navigate("overview") { popUpTo("auth") { inclusive = true } }
                }
                // If it is a new user, the biometric prompt will be triggered by the other LaunchedEffect
            }
            is SignInState.Error -> {
                Toast.makeText(context, state.message, Toast.LENGTH_LONG).show()
                authViewModel.resetSignInState()
            }
            else -> Unit // Handle Idle and Loading states if needed (e.g., show a spinner)
        }
    }

    // Biometric Prompt Logic
    LaunchedEffect(authViewModel.showBiometricPrompt) {
        if (authViewModel.showBiometricPrompt) {
            showBiometricPrompt(activity) {
                navController.navigate("overview") { popUpTo("auth") { inclusive = true } }
            }
            authViewModel.biometricPromptShown()
        }
    }

    // --- UI ---    
    when (signInState) {
        is SignInState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        else -> {
             Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(painter = painterResource(id = R.drawable.ic_spendlytics_logo), contentDescription = "Logo", modifier = Modifier.size(100.dp))
                Spacer(modifier = Modifier.height(16.dp))
                Text("Spendlytics", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(48.dp))

                // Google Sign-In Button
                Button(
                    onClick = {
                        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                            .requestIdToken("582248015830-2hoca89403pa6fdv8711frj9r12citk1.apps.googleusercontent.com") 
                            .requestEmail()
                            .build()
                        val googleSignInClient = GoogleSignIn.getClient(activity, gso)
                        googleSignInLauncher.launch(googleSignInClient.signInIntent)
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = Color.Black)
                ) {
                    Icon(painter = painterResource(id = R.drawable.ic_google_logo), contentDescription = null, modifier = Modifier.size(24.dp))
                    Spacer(modifier = Modifier.width(12.dp))
                    Text("Sign in with Google")
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Apple Sign-In Button (Not implemented)
                Button(
                    onClick = { Toast.makeText(context, "Apple Sign-In not yet implemented.", Toast.LENGTH_SHORT).show() },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Black, contentColor = Color.White)
                ) {
                    Icon(painter = painterResource(id = R.drawable.ic_apple_logo), contentDescription = null, modifier = Modifier.size(24.dp))
                    Spacer(modifier = Modifier.width(12.dp))
                    Text("Sign in with Apple")
                }
            }
        }
    }
}

private fun showBiometricPrompt(activity: FragmentActivity, onResult: (Boolean) -> Unit) {
    val biometricManager = BiometricManager.from(activity)
    if (biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG) != BiometricManager.BIOMETRIC_SUCCESS) {
        onResult(true)
        return
    }
    val promptInfo = BiometricPrompt.PromptInfo.Builder()
        .setTitle("Enable Biometric Login")
        .setSubtitle("Use biometrics for faster login")
        .setNegativeButtonText("Maybe later")
        .build()

    val biometricPrompt = BiometricPrompt(activity, ContextCompat.getMainExecutor(activity), object : BiometricPrompt.AuthenticationCallback() {
        override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
            onResult(true)
        }
        override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
            onResult(true) // Always proceed even if they cancel
        }
    })
    biometricPrompt.authenticate(promptInfo)
}
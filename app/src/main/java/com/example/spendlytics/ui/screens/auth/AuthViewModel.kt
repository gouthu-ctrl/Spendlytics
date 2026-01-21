package com.example.spendlytics.ui.screens.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spendlytics.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class SignInState {
    object Idle : SignInState()
    object Loading : SignInState()
    data class Success(val isNewUser: Boolean) : SignInState()
    data class Error(val message: String) : SignInState()
}

class AuthViewModel : ViewModel() {

    private val authRepository = AuthRepository()

    private val _signInState = MutableStateFlow<SignInState>(SignInState.Idle)
    val signInState = _signInState.asStateFlow()

    var showBiometricPrompt by mutableStateOf(false)
        private set

    fun signInWithGoogle(idToken: String) {
        viewModelScope.launch {
            _signInState.value = SignInState.Loading
            try {
                val authResult = authRepository.signInWithGoogle(idToken)
                val isNewUser = authResult.additionalUserInfo?.isNewUser ?: false
                _signInState.value = SignInState.Success(isNewUser)
                if (isNewUser) {
                    showBiometricPrompt = true
                }
            } catch (e: Exception) {
                _signInState.value = SignInState.Error(e.message ?: "An unknown error occurred.")
            }
        }
    }

    fun resetSignInState() {
        _signInState.value = SignInState.Idle
    }

    fun biometricPromptShown() {
        showBiometricPrompt = false
    }
}
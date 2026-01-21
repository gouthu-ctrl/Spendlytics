package com.finance.spendlytics.data.repository

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class AuthRepository {

    private val auth: FirebaseAuth = Firebase.auth
    private val firestoreRepository = FirestoreRepository()

    /**
     * Handles the sign-in with Google, checks if the user is new, and creates a user record in Firestore if they are.
     * @return AuthResult which contains user information and metadata.
     */
    suspend fun signInWithGoogle(idToken: String): AuthResult {
        val credential = com.google.firebase.auth.GoogleAuthProvider.getCredential(idToken, null)
        val authResult = auth.signInWithCredential(credential).await()

        val isNewUser = authResult.additionalUserInfo?.isNewUser ?: false

        if (isNewUser && authResult.user != null) {
            firestoreRepository.createUserProfile(authResult.user!!)
        }

        return authResult
    }
}
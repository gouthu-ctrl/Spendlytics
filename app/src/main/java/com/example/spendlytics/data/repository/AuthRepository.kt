package com.example.spendlytics.data.repository

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class AuthRepository {

    private val auth: FirebaseAuth = Firebase.auth
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    /**
     * Handles the sign-in with Google, checks if the user is new, and creates a user record in Firestore if they are.
     * @return AuthResult which contains user information and metadata.
     */
    suspend fun signInWithGoogle(idToken: String): AuthResult {
        val credential = com.google.firebase.auth.GoogleAuthProvider.getCredential(idToken, null)
        val authResult = auth.signInWithCredential(credential).await()

        val isNewUser = authResult.additionalUserInfo?.isNewUser ?: false

        if (isNewUser && authResult.user != null) {
            createUserInFirestore(authResult.user!!)
        }

        return authResult
    }

    /**
     * Creates a new user document in the 'users' collection in Firestore.
     */
    private suspend fun createUserInFirestore(user: FirebaseUser) {
        val userDocument = hashMapOf(
            "uid" to user.uid,
            "displayName" to user.displayName,
            "email" to user.email,
            "createdAt" to com.google.firebase.Timestamp.now()
        )
        db.collection("users").document(user.uid).set(userDocument).await()
    }
}
package com.finance.spendlytics.data.repository

import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class FirestoreRepository {

    private val db: FirebaseFirestore = Firebase.firestore

    /**
     * Creates a new household document with the given admin user.
     *
     * @param adminUid The UID of the user who will be the initial member.
     * @return The auto-generated ID of the new household document.
     */
    private suspend fun createNewHousehold(adminUid: String): String {
        val householdData = hashMapOf(
            "members" to listOf(adminUid)
        )

        val householdDocument = db.collection("households").add(householdData).await()
        return householdDocument.id
    }

    /**
     * Creates a profile for a new user, including creating their initial household.
     *
     * @param user The FirebaseUser object for the new user.
     */
    suspend fun createUserProfile(user: FirebaseUser) {
        // First, create a new household for this user.
        val householdId = createNewHousehold(user.uid)

        // Next, create the user's profile document.
        val userProfile = hashMapOf(
            "displayName" to user.displayName,
            "email" to user.email,
            "householdId" to householdId
        )

        // Write the new user document to the 'users' collection with their UID as the document ID.
        db.collection("users").document(user.uid).set(userProfile).await()
    }
}
package com.example.shoppinglist.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Auth repository
 *
 * @property firebaseAuth
 * @constructor Create empty Auth repository
 * https://firebase.google.com/docs/auth/android/start
 * https://www.section.io/engineering-education/integrating-firestore-in-android-kotlin/
 */
class AuthRepository @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) {

    val currentUser: FirebaseUser? = firebaseAuth.currentUser

    /**
     * Has user
     *
     * @return
     */
    fun hasUser(): Boolean = firebaseAuth.currentUser != null

    /**
     * Get user id
     *
     * @return
     */
    fun getUserId(): String = firebaseAuth.currentUser?.uid.orEmpty()


    /**
     * Create user
     *
     * @param email
     * @param password
     * @param onComplete
     * @receiver
     */
    suspend fun createUser(
        email: String,
        password: String,
        onComplete: (Boolean) -> Unit
    ) = withContext(Dispatchers.IO) {

        firebaseAuth
            .createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    onComplete.invoke(true)
                } else {
                    onComplete.invoke(false)
                }
            }.await()
    }


    /**
     * Login
     *
     * @param email
     * @param password
     * @param onComplete
     * @receiver
     */
    suspend fun login(
        email: String,
        password: String,
        onComplete: (Boolean) -> Unit
    ) = withContext(Dispatchers.IO) {

        firebaseAuth
            .signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    onComplete.invoke(true)
                } else {
                    onComplete.invoke(false)
                }
            }.await()
    }

    /**
     * Logout
     *
     */
    suspend fun logout() = withContext(Dispatchers.IO) {
        firebaseAuth.signOut()


    }

}
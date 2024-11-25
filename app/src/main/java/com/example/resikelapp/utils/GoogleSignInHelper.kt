package com.example.resikelapp.utils

import android.app.Activity
import android.content.Intent
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class GoogleSignInHelper(
    private val activity: Activity,
    private val auth: FirebaseAuth
) {
    private lateinit var googleSignInClient: GoogleSignInClient

    // Fungsi untuk keluar (sign out)
    fun signOut(onSuccess: () -> Unit) {
        googleSignInClient.signOut()
            .addOnCompleteListener {
                auth.signOut()
                onSuccess()
            }
    }

    // Inisialisasi GoogleSignInClient
    fun initGoogleSignInClient(webClientId: String) {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(webClientId)
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(activity, gso)
    }

    // Mengembalikan Intent untuk memulai Google Sign-In
    fun signInWithGoogle(): Intent {
        return googleSignInClient.signInIntent
    }

    // Menangani hasil login Google
    fun handleSignInResult(
        data: Intent?,
        onSuccess: (GoogleSignInAccount) -> Unit,
        onError: (String) -> Unit
    ) {
        val task = GoogleSignIn.getSignedInAccountFromIntent(data)
        if (task.isSuccessful) {
            val account = task.result
            account?.let { onSuccess(it) }
        } else {
            onError(task.exception?.localizedMessage ?: "Google Sign-In failed")
        }
    }

    // Mengautentikasi dengan Firebase menggunakan akun Google
    fun authenticateWithFirebase(
        account: GoogleSignInAccount,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onSuccess()
                } else {
                    onError(task.exception?.localizedMessage ?: "Authentication failed")
                }
            }
    }
}

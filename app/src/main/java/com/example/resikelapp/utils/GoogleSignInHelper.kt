package com.example.resikelapp.utils

import android.content.Context
import android.content.Intent
import androidx.activity.ComponentActivity
import com.example.resikelapp.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider

class GoogleSignInHelper(
    private val activity: ComponentActivity,
    private val auth: FirebaseAuth
) {
    private lateinit var googleSignInClient: GoogleSignInClient

    // Inisialisasi GoogleSignInClient
    fun initGoogleSignInClient(webClientId: String) {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(webClientId)
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(activity, gso)
    }

    // Fungsi untuk mendapatkan Google Credential
    fun getGoogleCredential(account: GoogleSignInAccount): AuthCredential {
        return GoogleAuthProvider.getCredential(account.idToken, null)
    }

    // Fungsi untuk memulai proses Google Sign-In
    fun signInWithGoogle(): Intent? {
        return if (::googleSignInClient.isInitialized) {
            googleSignInClient.signInIntent
        } else {
            null // Menghindari penggunaan sebelum inisialisasi
        }
    }

    // Fungsi untuk menangani hasil dari Google Sign-In
    fun handleSignInResult(
        data: Intent?,
        onSuccess: (GoogleSignInAccount) -> Unit,
        onError: (String) -> Unit
    ) {
        val task = GoogleSignIn.getSignedInAccountFromIntent(data)
        try {
            val account = task.result
            if (account != null) {
                onSuccess(account)
            } else {
                onError("Google Sign-In gagal. Akun tidak ditemukan.")
            }
        } catch (e: Exception) {
            onError("Google Sign-In gagal: ${e.message}")
        }
    }

    // Autentikasi dengan Firebase menggunakan Google Account
    fun authenticateWithFirebase(
        account: GoogleSignInAccount,
        onSuccess: (FirebaseUser) -> Unit,
        onError: (String) -> Unit
    ) {
        val credential: AuthCredential = GoogleAuthProvider.getCredential(account.idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    if (user != null) {
                        onSuccess(user)
                    } else {
                        onError("Gagal mendapatkan data pengguna dari Firebase.")
                    }
                } else {
                    onError(task.exception?.message ?: "Firebase Authentication gagal.")
                }
            }
    }

    // Fungsi untuk logout dari Google dan Firebase
    fun signOut(onComplete: () -> Unit) {
        googleSignInClient.signOut()
            .addOnCompleteListener {
                auth.signOut()
                onComplete()
            }
    }
}

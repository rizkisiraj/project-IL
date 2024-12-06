package com.example.resikelapp.ui.screens.auth

import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.resikelapp.utils.GoogleSignInHelper
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

sealed class AuthResult {
    object Loading : AuthResult()
    data class Success(val user: FirebaseUser) : AuthResult()
    data class Error(val message: String) : AuthResult()
}

class AuthViewModel(
    private val googleSignInHelper: GoogleSignInHelper,
    private val auth: FirebaseAuth
) : ViewModel() {

    private val _authResult = MutableLiveData<AuthResult?>()
    val authResult: LiveData<AuthResult?> get() = _authResult

    private val _user = MutableLiveData<FirebaseUser?>()
    val user: LiveData<FirebaseUser?> get() = _user

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    init {
        _user.value = auth.currentUser
    }

    // Fungsi untuk login Google
    fun signInWithGoogleIntent(): Intent? {
        return googleSignInHelper.signInWithGoogle()
    }

    fun handleGoogleSignInResult(data: Intent?) {
        _authResult.value = AuthResult.Loading
        googleSignInHelper.handleSignInResult(
            data,
            onSuccess = { account -> handleGoogleSignIn(account) },
            onError = { error -> _authResult.value = AuthResult.Error(error) }
        )
    }

    private fun handleGoogleSignIn(account: GoogleSignInAccount) {
        val email = account.email
        if (email != null) {
            auth.fetchSignInMethodsForEmail(email)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val signInMethods = task.result?.signInMethods ?: emptyList()
                        if (signInMethods.contains("password")) {
                            linkGoogleAccountToEmail(account, email)
                        } else {
                            authenticateWithFirebase(account)
                        }
                    } else {
                        _authResult.value = AuthResult.Error(task.exception?.message ?: "Gagal memeriksa metode login.")
                    }
                }
        } else {
            _authResult.value = AuthResult.Error("Email Google tidak ditemukan.")
        }
    }

    private fun linkGoogleAccountToEmail(account: GoogleSignInAccount, email: String) {
        val credential = googleSignInHelper.getGoogleCredential(account)
        auth.currentUser?.linkWithCredential(credential)
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    authenticateWithFirebase(account)
                } else {
                    _authResult.value = AuthResult.Error(task.exception?.message ?: "Gagal menghubungkan akun Google.")
                }
            }
    }

    private fun authenticateWithFirebase(account: GoogleSignInAccount) {
        _authResult.value = AuthResult.Loading
        googleSignInHelper.authenticateWithFirebase(
            account,
            onSuccess = { firebaseUser ->
                _authResult.value = AuthResult.Success(firebaseUser)
                _user.value = firebaseUser
            },
            onError = { error -> _authResult.value = AuthResult.Error(error) }
        )
    }

    // Fungsi login email & password
    fun loginWithEmail(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        if (email.isBlank() || password.isBlank()) {
            onError("Email dan password tidak boleh kosong.")
            return
        }

        _authResult.value = AuthResult.Loading
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    if (user != null) {
                        _authResult.value = AuthResult.Success(user)
                        _user.value = user
                        onSuccess()
                    } else {
                        onError("Gagal mendapatkan data pengguna.")
                    }
                } else {
                    onError(task.exception?.message ?: "Login gagal.")
                }
            }
    }

    // Fungsi untuk memeriksa apakah pengguna terhubung dengan akun Google
    fun checkUserWithGoogleSignIn(
        email: String,
        onGoogleAccountFound: () -> Unit,
        onNotGoogleAccount: () -> Unit
    ) {
        auth.fetchSignInMethodsForEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val signInMethods = task.result?.signInMethods ?: emptyList()
                    if (signInMethods.contains("google.com")) {
                        onGoogleAccountFound()
                    } else {
                        onNotGoogleAccount()
                    }
                } else {
                    onNotGoogleAccount()
                }
            }
    }

    // Fungsi untuk mengirim email verifikasi
    fun sendEmailVerification(
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        val currentUser = auth.currentUser
        currentUser?.sendEmailVerification()
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onSuccess()
                } else {
                    onError(task.exception?.message ?: "Gagal mengirim email verifikasi.")
                }
            } ?: onError("Pengguna tidak ditemukan.")
    }

    // Fungsi untuk logout
    fun signOut() {
        _isLoading.value = true
        googleSignInHelper.signOut {
            auth.signOut()
            _isLoading.value = false
            _user.value = null
        }
    }

    // Fungsi untuk membersihkan hasil autentikasi
    fun clearAuthResult() {
        _authResult.value = null
    }
}

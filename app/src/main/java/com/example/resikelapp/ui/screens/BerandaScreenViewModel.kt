package com.example.resikelapp.ui.screens

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.resikelapp.data.model.Acara
import com.example.resikelapp.data.model.News
import com.example.resikelapp.data.model.UserRegistration
import com.example.resikelapp.data.repository.ResikelRepository
import com.example.resikelapp.utils.StoreUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BerandaScreenViewModel(private val repository: ResikelRepository): ViewModel() {
    private var _newsList = MutableStateFlow<List<News>>(emptyList())
    var newsList = _newsList.asStateFlow()

    private var _user = MutableStateFlow(UserRegistration())
    var user = _user.asStateFlow()

    private var _total = MutableStateFlow<Int>(0)
    var total = _total.asStateFlow()

    private var _isLoading = MutableStateFlow<Boolean>(true)
    var isLoading = _isLoading.asStateFlow()

    private var _isError = MutableStateFlow<String?>(null)
    var isError = _isError.asStateFlow()

    private var _acaraComunities = MutableStateFlow<List<Acara>>(emptyList())
    var acaraComunities = _acaraComunities.asStateFlow()

    val db = Firebase.firestore

    fun fetchDataAndSum(userId: String) {
        viewModelScope.launch {
            db.collection("transaksi")
                .whereEqualTo("idUser", userId)
                .get()
                .addOnSuccessListener { snapshot ->
                    val totalSum = snapshot.documents
                        .mapNotNull { it.getLong("totalPoints")?.toInt() }
                        .sum()
                    _total.value = totalSum
                }
                .addOnFailureListener { exception ->
                    // Handle failure if needed
                    println("Error fetching data: ${exception.message}")
                }
        }
    }

    fun getNewsFirebase() {
        _isLoading.value = true
        db.collection("news")
            .addSnapshotListener { value, error ->
                if (error != null) {
                    _isLoading.value = false
                    return@addSnapshotListener
                }

                if (value != null) {
                    _isLoading.value = false
                    _newsList.value = value.toObjects(News::class.java)
                }

            }
    }

    fun getAcaraFirebase() {
        db.collection("acara")
            .addSnapshotListener { value, error ->
                if (error != null) {
                    return@addSnapshotListener
                }

                if (value != null) {
                    _acaraComunities.value = value.toObjects(Acara::class.java)
                }
            }
    }

    fun getUserData(id: String, dataStore: StoreUser) {
        db.collection("users")
            .document(id)
            .addSnapshotListener { documentSnapshot, error ->
                if (error != null) {
                    return@addSnapshotListener
                }

                if (documentSnapshot != null && documentSnapshot.exists()) {
                    val userSnapshot = documentSnapshot.toObject(UserRegistration::class.java)
                    if (userSnapshot != null) {
                        Log.d("ini", userSnapshot.name)
                        _user.value = userSnapshot
                        viewModelScope.launch {
                            dataStore.saveName(userSnapshot.name)
                        }
                    }
                }
            }

    }

}
package com.example.resikelapp.ui.screens

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.resikelapp.data.model.Acara
import com.example.resikelapp.data.model.JadwalPenjemputan
import com.example.resikelapp.data.model.News
import com.example.resikelapp.data.model.UserRegistration
import com.example.resikelapp.data.repository.ResikelRepository
import com.example.resikelapp.utils.StoreUser
import com.google.firebase.Timestamp
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.*

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

    private var _submittedStatus= MutableStateFlow<String>("Belum Terdaftar")
    var submittedStatus = _submittedStatus.asStateFlow()

    private var _acaraComunities = MutableStateFlow<List<Acara>>(emptyList())
    var acaraComunities = _acaraComunities.asStateFlow()

    private var _jadwalPenjemputan = MutableStateFlow<JadwalPenjemputan?>(null)
    var jadwalPenjemputan = _jadwalPenjemputan.asStateFlow()

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
                            dataStore.savePhoto(userSnapshot.fotoProfil)
                        }
                    }
                }
            }

    }

    fun getTanggalPenjemputanTerdekat() {
        viewModelScope.launch {
            try {
                val todayStart = Calendar.getInstance().apply {
                    set(Calendar.HOUR_OF_DAY, 0)
                    set(Calendar.MINUTE, 0)
                    set(Calendar.SECOND, 0)
                    set(Calendar.MILLISECOND, 0)
                }.time

                db.collection("jadwalPenjemputan")
                    .whereGreaterThanOrEqualTo("tanggal", Timestamp(todayStart))
                    .orderBy("tanggal", Query.Direction.ASCENDING)
                    .limit(1)
                    .get()
                    .addOnSuccessListener { documents ->
                        if (!documents.isEmpty) {
                            val document = documents.documents.first()
                            val jadwal = JadwalPenjemputan(
                                id = document.id,
                                tanggal = document.getTimestamp("tanggal") ?: Timestamp.now()
                            )
                            _jadwalPenjemputan.value = jadwal
                        } else {
                            _jadwalPenjemputan.value = null
                        }
                    }
                    .addOnFailureListener { exception ->
                        exception.printStackTrace()
                    }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun checkIfTheUserAlreadySubmittedPenjemputan(userId: String, penjemputanId: String) {
        db.collection("daftarPenjemputan")
            .whereEqualTo("idUser", userId)
            .whereEqualTo("idPenjemputan", penjemputanId)
            .limit(1)
            .get()
            .addOnSuccessListener { querySnapshot ->
                if (!querySnapshot.isEmpty) {
                    val document = querySnapshot.documents.first()

                    val status = document.getString("status")
                    Log.d("halo", status!!)
                    _submittedStatus.value = status ?: "Belum Terdaftar"
                } else {
                    Log.d("halo", "ini gak masuk")
                    _submittedStatus.value = "Belum Terdaftar"
                }
            }
            .addOnFailureListener { exception ->
                // Log or handle the failure (e.g., network issues)
                exception.printStackTrace()
            }
    }

}
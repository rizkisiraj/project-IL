package com.example.resikelapp.ui.screens.profile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.resikelapp.data.model.JadwalPenjemputan
import com.example.resikelapp.data.model.UserRegistration
import com.example.resikelapp.utils.StoreUser
import com.google.firebase.Timestamp
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.*

class ProfileViewModel(): ViewModel() {
    private var _user = MutableStateFlow(UserRegistration())
    var user = _user.asStateFlow()

    private var _jadwalPenjemputan = MutableStateFlow<JadwalPenjemputan?>(null)
    var jadwalPenjemputan = _jadwalPenjemputan.asStateFlow()

    private var _totalObject = MutableStateFlow<Map<String, Int>>(mapOf(
        "totalPoints" to 0, "totalKilos" to 0
    ))
    var totalObject = _totalObject.asStateFlow()

    val db = Firebase.firestore

    fun fetchDataAndSum(userId: String) {
        viewModelScope.launch {
            db.collection("transaksi")
                .whereEqualTo("idUser", userId)
                .whereEqualTo("status", "sukses")
                .get()
                .addOnSuccessListener { snapshot ->
                    val totalPoints = snapshot.documents
                        .mapNotNull { it.getLong("totalPoints")?.toInt() }
                        .sum()
                    _totalObject.value = _totalObject.value.toMutableMap().apply {
                        put("totalPoints", totalPoints)
                        put("totalKilos", totalPoints/10)
                    }
                }
                .addOnFailureListener { exception ->
                    println("Error fetching data: ${exception.message}")
                }
        }
    }

    fun getUserData(id: String) {
        db.collection("users")
            .document(id)
            .addSnapshotListener { documentSnapshot, error ->
                if (error != null) {
                    return@addSnapshotListener
                }

                if (documentSnapshot != null && documentSnapshot.exists()) {
                    val userSnapshot = documentSnapshot.toObject(UserRegistration::class.java)
                    if (userSnapshot != null) {
                        _user.value = userSnapshot
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
}
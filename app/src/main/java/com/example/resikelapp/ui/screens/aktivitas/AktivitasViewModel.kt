package com.example.resikelapp.ui.screens.aktivitas

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.resikelapp.data.model.News
import com.example.resikelapp.data.model.SampahTransaksi
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class AktivitasViewModel: ViewModel() {
    private var _listTransaksi = MutableStateFlow<List<SampahTransaksi>>(emptyList())
    var listTransaksi = _listTransaksi.asStateFlow()

    val db = Firebase.firestore

    fun getAktivitasFirebase(userId: String) {
        db.collection("transaksi")
            .whereEqualTo("idUser", userId)
            .addSnapshotListener { value, error ->
                if (error != null) {
                    return@addSnapshotListener
                }

                if (value != null) {
                    _listTransaksi.value = value.toObjects(SampahTransaksi::class.java)
                }

            }
        Log.d("masuk", "masuk ke firebase")
    }


}
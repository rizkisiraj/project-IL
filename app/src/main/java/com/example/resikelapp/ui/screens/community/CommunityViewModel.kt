package com.example.resikelapp.ui.screens.community


import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.resikelapp.data.model.Acara
import com.example.resikelapp.data.model.Community
import com.example.resikelapp.data.model.OrderCommunity
import com.example.resikelapp.data.repository.ResikelRepository
import com.example.resikelapp.utils.UiState
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObjects
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CommunityViewModel(private val repository: ResikelRepository): ViewModel() {
    private var _acaraComunities = MutableStateFlow<List<Acara>>(emptyList())
    var acaraComunities = _acaraComunities.asStateFlow()
    val db = Firebase.firestore

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

    private var _groupedComunities = MutableStateFlow<List<Community>>(emptyList())
    var groupedComunities = _groupedComunities.asStateFlow()

    fun getCommunityFirebase() {
        db.collection("community")
            .addSnapshotListener { value, error ->
                if (error != null) {
                    return@addSnapshotListener
                }

                if (value != null) {
                    _groupedComunities.value = value.toObjects(Community::class.java)
                }
            }
    }

    init {
        getCommunityFirebase()
        getAcaraFirebase()
    }

    private var _query = MutableStateFlow("")
    val query = _query.asStateFlow()

    val filteredCommunities = combine(_groupedComunities, _query) { communities, query ->
        if (query.isBlank()) {
            communities // Tampilkan semua komunitas jika query kosong
        } else {
            communities.filter { it.nama.contains(query, ignoreCase = true) }
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _gabungStatus = MutableStateFlow(false)
    val gabungStatus: StateFlow<Boolean> get() = _gabungStatus

    fun search(newQuery: String) {
        _query.value = newQuery
    }

    private val _uiState: MutableStateFlow<UiState<Community>> =
        MutableStateFlow(UiState.Loading)

    val uiState: StateFlow<UiState<Community>>
        get() = _uiState

    fun getOrderCommunityById(communityId: String) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            val docRef = db.collection("community").document(communityId)
            docRef.get()
                .addOnSuccessListener { document ->
                    if (document != null && document.exists()) {
                        val community = document.toObject(Community::class.java)
                        if (community != null) {
                            _uiState.value = UiState.Success(community)
                            _gabungStatus.value = community.gabungStatus
                        } else {
                            _uiState.value = UiState.Error("Data tidak valid.")
                        }
                    } else {
                        Log.d("DAPAT DATA", "No such document")
                        _uiState.value = UiState.Error("Dokumen tidak ditemukan.")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d("DAPAT DATA", "get failed with ", exception)
                    _uiState.value = UiState.Error("Gagal mendapatkan data: ${exception.message}")
                }
        }
    }


    fun updateGabungStatus(communityId: String, status: Boolean) {
        viewModelScope.launch {
            val statusBergabung = hashMapOf(
                "gabungStatus" to status,
            )

            db.collection("community").document(communityId)
                .set(statusBergabung, SetOptions.merge())
                .addOnSuccessListener {
                    _gabungStatus.value = status
                    Log.d("CommunityViewModel", "DocumentSnapshot successfully written!")
                }
                .addOnFailureListener { e -> Log.w("CommunityViewModel", "Error writing document", e) }

        }
    }

}
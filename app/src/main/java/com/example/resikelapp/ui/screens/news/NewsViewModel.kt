package com.example.resikelapp.ui.screens.news


import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.resikelapp.data.model.Acara
import com.example.resikelapp.data.model.Community
import com.example.resikelapp.data.model.News
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

class NewsViewModel(private val repository: ResikelRepository): ViewModel() {
    private var _newsList = MutableStateFlow<List<News>>(emptyList())
    var newsList = _newsList.asStateFlow()

    private var _isLoading = MutableStateFlow<Boolean>(true)
    var isLoading = _isLoading.asStateFlow()

    private var _isError = MutableStateFlow<String?>(null)
    var isError = _isError.asStateFlow()

    private var _newsDetail = MutableStateFlow<News?>(null)
    var newsDetail = _newsDetail.asStateFlow()

    val db = Firebase.firestore

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

    fun getNewsById(newsId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            val docRef = db.collection("news").document(newsId)
            docRef.get()
                .addOnSuccessListener { document ->
                    if (document != null && document.exists()) {
                        val news = document.toObject(News::class.java)
                        if (news != null) {
                            _isLoading.value = false
                            _newsDetail.value = news
                        } else {
                            _isError.value = "Gagal memuat artikel, artikel tidak ada"
                        }
                    } else {
                        Log.d("DAPAT DATA", "No such document")
                        _isError.value = "Gagal memuat artikel, artikel tidak ada"
                    }
                    _isLoading.value = false
                }
                .addOnFailureListener { exception ->
                    Log.d("DAPAT DATA", "get failed with ", exception)
                    _isLoading.value = false
                    _isError.value = "Gagal memuat artikel, artikel tidak ada ${exception.message}"
                }
        }
    }

}
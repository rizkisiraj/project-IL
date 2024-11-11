package com.example.resikelapp.ui.screens.community


import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.resikelapp.data.model.Community
import com.example.resikelapp.data.model.OrderCommunity
import com.example.resikelapp.data.repository.ResikelRepository
import com.example.resikelapp.utils.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CommunityViewModel(private val repository: ResikelRepository): ViewModel() {
    private val _groupedComunities = MutableStateFlow(
        repository.getCommunity()
            .sortedBy { it.nama }
            .groupBy { it.nama[0] }
    )
    val groupedComunities: StateFlow<Map<Char, List<Community>>> get() = _groupedComunities

    private val _query = mutableStateOf("")
    val query: State<String> get() = _query

    private val _gabungStatus = MutableStateFlow(false)
    val gabungStatus: StateFlow<Boolean> get() = _gabungStatus

    fun search(newQuery: String) {
        _query.value = newQuery
        _groupedComunities.value = repository.searchCommunity(_query.value)
            .sortedBy { it.nama }
            .groupBy { it.nama[0]}
    }
    private val _uiState: MutableStateFlow<UiState<OrderCommunity>> =
        MutableStateFlow(UiState.Loading)

    val uiState: StateFlow<UiState<OrderCommunity>>
        get() = _uiState

    fun getOrderCommunityById(communityId: Long) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            val orderCommunity = repository.getOrderCommunityById(communityId)
            _gabungStatus.value = orderCommunity.community.gabungStatus
            _uiState.value = UiState.Success(repository.getOrderCommunityById(communityId))
        }
    }

    fun updateGabungStatus(communityId: Long, status: Boolean) {
        viewModelScope.launch {
            repository.updateGabungStatus(communityId, status)
            _gabungStatus.value = status
            }
        }

}

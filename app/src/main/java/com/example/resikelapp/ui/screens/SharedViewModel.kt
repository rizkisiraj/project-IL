package com.example.resikelapp.ui.screens

import androidx.lifecycle.ViewModel
import com.example.resikelapp.data.model.News
import com.example.resikelapp.data.model.SampahItem
import com.example.resikelapp.data.model.SampahTransaksi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SharedViewModel: ViewModel() {
    private var _sampahItems = MutableStateFlow<List<SampahItem>>(emptyList())
    var sampahItems = _sampahItems.asStateFlow()

    private var _sampahTransaksi = MutableStateFlow<SampahTransaksi>(SampahTransaksi())
    var sampahTransaksi = _sampahTransaksi.asStateFlow()

    private var _totalPoin = MutableStateFlow<Int>(0)
    var totalPoin = _totalPoin.asStateFlow()

    fun updateSampahTransaksi(sampahTransaksi: SampahTransaksi) {
        _sampahTransaksi.value = sampahTransaksi
    }

    fun updateSampahItems(sampahFromBefore: List<SampahItem>) {
        _sampahItems.value = sampahFromBefore

        _totalPoin.value = sampahFromBefore.sumOf { it.points }
    }
}
package com.example.resikelapp.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.resikelapp.data.repository.ResikelRepository
import com.example.resikelapp.ui.screens.BerandaScreenViewModel
import com.example.resikelapp.ui.screens.community.CommunityViewModel
import com.example.resikelapp.ui.screens.news.NewsViewModel

class ViewModelFactory(private val repository: ResikelRepository) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CommunityViewModel::class.java)) {
            return CommunityViewModel(repository) as T
        }
        if (modelClass.isAssignableFrom(NewsViewModel::class.java)) {
            return NewsViewModel(repository) as T
        }
        if (modelClass.isAssignableFrom(BerandaScreenViewModel::class.java)) {
            return BerandaScreenViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
}
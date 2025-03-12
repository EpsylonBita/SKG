package com.greekevents.presentation.search

import android.util.Log
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * ViewModel for the search functionality.
 * Handles search logic and provides search results to the UI.
 */
@HiltViewModel
class SearchViewModel @Inject constructor() : ViewModel() {

    companion object {
        private const val TAG = "SearchViewModel"
    }

    init {
        Log.d(TAG, "SearchViewModel initialized")
    }

    // This will be expanded with actual search functionality
    fun search(query: String) {
        Log.d(TAG, "Searching for: $query")
        // In the future, this will call a repository to fetch search results
    }

    override fun onCleared() {
        Log.d(TAG, "SearchViewModel cleared")
        super.onCleared()
    }
} 
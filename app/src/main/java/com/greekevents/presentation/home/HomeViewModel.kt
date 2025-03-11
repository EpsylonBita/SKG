package com.greekevents.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.greekevents.domain.model.Category
import com.greekevents.domain.model.Event
import com.greekevents.domain.repository.EventRepository
import com.greekevents.domain.repository.RecommendationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for the Home screen that manages the UI state and business logic.
 * 
 * This ViewModel fetches and manages different types of events and categories
 * to be displayed on the home screen.
 */
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val eventRepository: EventRepository,
    private val recommendationRepository: RecommendationRepository
) : ViewModel() {

    // Recommended events for the user
    private val _recommendedEvents = MutableStateFlow<List<Event>>(emptyList())
    val recommendedEvents: StateFlow<List<Event>> = _recommendedEvents.asStateFlow()

    // Popular events among all users
    private val _popularEvents = MutableStateFlow<List<Event>>(emptyList())
    val popularEvents: StateFlow<List<Event>> = _popularEvents.asStateFlow()

    // Nearby events based on user location
    private val _nearbyEvents = MutableStateFlow<List<Event>>(emptyList())
    val nearbyEvents: StateFlow<List<Event>> = _nearbyEvents.asStateFlow()

    // Event categories
    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories: StateFlow<List<Category>> = _categories.asStateFlow()

    // Loading state
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    // Error message
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    init {
        loadData()
    }

    /**
     * Load all data needed for the home screen.
     */
    private fun loadData() {
        _isLoading.value = true
        _errorMessage.value = null
        
        // Load recommended events
        viewModelScope.launch {
            try {
                recommendationRepository.getPersonalizedRecommendations()
                    .catch { e ->
                        handleError("Error loading recommendations: ${e.message}")
                    }
                    .collect { events ->
                        _recommendedEvents.value = events
                    }
            } catch (e: Exception) {
                handleError("Error loading recommendations: ${e.message}")
            }
        }
        
        // Load popular events
        viewModelScope.launch {
            try {
                recommendationRepository.getTrendingEvents()
                    .catch { e ->
                        handleError("Error loading popular events: ${e.message}")
                    }
                    .collect { events ->
                        _popularEvents.value = events
                    }
            } catch (e: Exception) {
                handleError("Error loading popular events: ${e.message}")
            }
        }
        
        // Load nearby events
        // In a real app, we would get the user's location first
        viewModelScope.launch {
            try {
                // Using default Athens coordinates as fallback
                recommendationRepository.getLocationBasedRecommendations(
                    latitude = 37.9838, // Athens latitude
                    longitude = 23.7275  // Athens longitude
                )
                    .catch { e ->
                        handleError("Error loading nearby events: ${e.message}")
                    }
                    .collect { events ->
                        _nearbyEvents.value = events
                    }
            } catch (e: Exception) {
                handleError("Error loading nearby events: ${e.message}")
            }
        }
        
        // Load categories
        viewModelScope.launch {
            try {
                eventRepository.getAllCategories()
                    .catch { e ->
                        handleError("Error loading categories: ${e.message}")
                    }
                    .collect { categoryList ->
                        _categories.value = categoryList
                    }
            } catch (e: Exception) {
                handleError("Error loading categories: ${e.message}")
            }
            
            // All data loaded
            _isLoading.value = false
        }
    }
    
    /**
     * Handle errors that occur during data loading.
     * 
     * @param message Error message to display
     */
    private fun handleError(message: String) {
        _errorMessage.value = message
        _isLoading.value = false
    }
    
    /**
     * Refresh all data when requested by the user.
     */
    fun refreshData() {
        loadData()
    }
}

/* Explanation for junior developers:
 * 1. @HiltViewModel annotation tells Hilt this ViewModel can be injected
 * 
 * 2. Constructor Injection:
 *    - @Inject constructor tells Hilt to provide the dependencies
 *    - eventRepository and recommendationRepository are injected by Hilt
 * 
 * 3. State Management:
 *    - MutableStateFlow is used to hold mutable state that can be updated
 *    - StateFlow (immutable) is exposed to the UI to observe changes
 *    - asStateFlow() converts MutableStateFlow to StateFlow for safer public exposure
 * 
 * 4. Coroutines:
 *    - viewModelScope.launch creates coroutines tied to the ViewModel lifecycle
 *    - When the ViewModel is cleared, all coroutines are automatically canceled
 * 
 * 5. Error Handling:
 *    - try-catch blocks catch exceptions from repository calls
 *    - catch operator handles errors in the Flow pipeline
 *    - handleError centralizes error handling logic
 * 
 * 6. Data Loading:
 *    - init block calls loadData() when the ViewModel is created
 *    - refreshData() allows manual refresh from the UI
 *    - Each data type is loaded in a separate coroutine for parallel loading
 */ 
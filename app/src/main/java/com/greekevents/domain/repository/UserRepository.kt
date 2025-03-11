package com.greekevents.domain.repository

import com.greekevents.domain.model.AuthState
import com.greekevents.domain.model.Event
import com.greekevents.domain.model.SearchHistoryItem
import com.greekevents.domain.model.User
import com.greekevents.domain.model.UserPreferences
import com.greekevents.domain.model.ViewedEventHistoryItem
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for user authentication, profile management,
 * and user-related data operations.
 */
interface UserRepository {

    /**
     * Get the current authentication state.
     * 
     * @return Flow emitting the current auth state, which updates when auth changes
     */
    fun getAuthState(): Flow<AuthState>
    
    /**
     * Sign in a user with email and password.
     * 
     * @param email User's email address
     * @param password User's password
     * @return Result indicating success or failure with error message
     */
    suspend fun signIn(email: String, password: String): Result<User>
    
    /**
     * Register a new user with email and password.
     * 
     * @param email User's email address
     * @param password User's password
     * @param displayName Optional display name for the user
     * @return Result indicating success or failure with error message
     */
    suspend fun register(email: String, password: String, displayName: String? = null): Result<User>
    
    /**
     * Sign out the current user.
     */
    suspend fun signOut()
    
    /**
     * Send a password reset email.
     * 
     * @param email User's email address
     * @return Result indicating success or failure with error message
     */
    suspend fun resetPassword(email: String): Result<Unit>
    
    /**
     * Get the current user's profile.
     * 
     * @return Flow emitting the current user's profile or null if not signed in
     */
    fun getUserProfile(): Flow<User?>
    
    /**
     * Update the user's profile information.
     * 
     * @param displayName New display name
     * @param photoUrl New photo URL
     * @return Result indicating success or failure with error message
     */
    suspend fun updateProfile(displayName: String? = null, photoUrl: String? = null): Result<User>
    
    /**
     * Update the user's preferences.
     * 
     * @param preferences New user preferences
     * @return Result indicating success or failure with error message
     */
    suspend fun updatePreferences(preferences: UserPreferences): Result<User>
    
    /**
     * Add a category to the user's favorite categories.
     * 
     * @param categoryId Category ID to add to favorites
     * @return Result indicating success or failure with error message
     */
    suspend fun addFavoriteCategory(categoryId: String): Result<Unit>
    
    /**
     * Remove a category from the user's favorite categories.
     * 
     * @param categoryId Category ID to remove from favorites
     * @return Result indicating success or failure with error message
     */
    suspend fun removeFavoriteCategory(categoryId: String): Result<Unit>
    
    /**
     * Get the user's favorite events.
     * 
     * @return Flow emitting a list of the user's favorite events
     */
    fun getFavoriteEvents(): Flow<List<Event>>
    
    /**
     * Add an event to the user's favorites.
     * 
     * @param eventId Event ID to add to favorites
     * @return Result indicating success or failure with error message
     */
    suspend fun addFavoriteEvent(eventId: String): Result<Unit>
    
    /**
     * Remove an event from the user's favorites.
     * 
     * @param eventId Event ID to remove from favorites
     * @return Result indicating success or failure with error message
     */
    suspend fun removeFavoriteEvent(eventId: String): Result<Unit>
    
    /**
     * Get the user's search history.
     * 
     * @param limit Maximum number of history items to return
     * @return Flow emitting a list of the user's search history items
     */
    fun getSearchHistory(limit: Int = 10): Flow<List<SearchHistoryItem>>
    
    /**
     * Add a search query to the user's search history.
     * 
     * @param query Search query
     * @param location Optional location filter
     * @param startDate Optional start date filter
     * @param endDate Optional end date filter
     * @param resultCount Number of results found
     * @return Result indicating success or failure with error message
     */
    suspend fun addSearchHistoryItem(
        query: String,
        location: String? = null,
        startDate: String? = null,
        endDate: String? = null,
        resultCount: Int? = null
    ): Result<Unit>
    
    /**
     * Clear the user's search history.
     * 
     * @return Result indicating success or failure with error message
     */
    suspend fun clearSearchHistory(): Result<Unit>
    
    /**
     * Get the user's viewed event history.
     * 
     * @param limit Maximum number of history items to return
     * @return Flow emitting a list of the user's viewed event history items
     */
    fun getViewedEventHistory(limit: Int = 20): Flow<List<ViewedEventHistoryItem>>
    
    /**
     * Add an event to the user's viewed event history.
     * 
     * @param eventId ID of the viewed event
     * @param eventTitle Title of the viewed event
     * @param categoryId Optional category ID of the viewed event
     * @return Result indicating success or failure with error message
     */
    suspend fun addViewedEvent(
        eventId: String,
        eventTitle: String,
        categoryId: String? = null
    ): Result<Unit>
}

// Explanation for junior developers:
// 1. The UserRepository interface defines all user-related operations
//    - Authentication (sign in, register, sign out, reset password)
//    - Profile management (get/update profile, preferences)
//    - User-specific data (favorites, history, recommendations)
//
// 2. The Result<T> return type provides a way to handle success or failure
//    - Success contains the requested data or Unit for operations with no return value
//    - Failure contains an error message explaining what went wrong
//
// 3. Flow<T> is used for data that might change over time
//    - For example, the authentication state or user profile
//    - Components can observe these flows and react to changes
//
// 4. The interface separates different concerns:
//    - Authentication (sign in, register, sign out)
//    - Profile management (update profile, preferences)
//    - Favorites (add/remove favorite events/categories)
//    - History tracking (search history, viewed events)
//
// 5. All operations are suspending or return Flow
//    - This makes them non-blocking and efficient
//    - They can be called from coroutines without blocking the main thread 
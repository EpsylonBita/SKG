package com.greekevents.domain.model

/**
 * Domain model class that represents a user in the application.
 * 
 * This class contains user information used for authentication and
 * displaying user profile details.
 */
data class User(
    val id: String,
    val email: String,
    val displayName: String? = null,
    val photoUrl: String? = null,
    val favoriteCategories: List<String> = emptyList(),
    val preferences: UserPreferences = UserPreferences()
)

/**
 * Domain model class that represents user preferences.
 * 
 * These preferences are used to personalize the user experience,
 * including notification settings and app appearance.
 */
data class UserPreferences(
    val notificationsEnabled: Boolean = true,
    val emailNotificationsEnabled: Boolean = true,
    val recommendationsEnabled: Boolean = true,
    val darkModeEnabled: Boolean? = null, // null means system default
    val selectedLanguage: String = "el" // Default to Greek
)

/**
 * Data class representing user authentication state.
 */
data class AuthState(
    val isLoggedIn: Boolean = false,
    val user: User? = null,
    val authToken: String? = null
)

/**
 * Data class representing a user's search history item.
 */
data class SearchHistoryItem(
    val id: String,
    val userId: String,
    val query: String,
    val timestamp: Long,
    val location: String? = null,
    val startDate: String? = null,
    val endDate: String? = null,
    val resultCount: Int? = null
)

/**
 * Data class representing a user's viewed event history item.
 */
data class ViewedEventHistoryItem(
    val id: String,
    val userId: String,
    val eventId: String,
    val eventTitle: String,
    val timestamp: Long,
    val categoryId: String? = null
)

// Explanation for junior developers:
// 1. The User class represents a user account in our application
//    - It contains basic information like ID, email, and display name
//    - It also includes user preferences and favorite categories
//
// 2. The UserPreferences class encapsulates user-specific settings
//    - These preferences affect how the app behaves for this specific user
//    - Default values are provided for all properties
//
// 3. The AuthState class tracks the current authentication state
//    - It contains a flag indicating if the user is logged in
//    - It also holds the user object and authentication token when authenticated
//
// 4. The SearchHistoryItem and ViewedEventHistoryItem classes track user behavior
//    - These are used for personalization and recommendations
//    - Each item is associated with a specific user through userId
//
// 5. Using separate classes for different aspects of user data helps maintain
//    single responsibility principle and makes the code more modular 
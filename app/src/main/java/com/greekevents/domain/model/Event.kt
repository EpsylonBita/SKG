package com.greekevents.domain.model

import java.util.Date

/**
 * Domain model class that represents an event.
 * 
 * This class contains all the information about an event that is displayed in the app.
 * It is used across different layers of the application.
 */
data class Event(
    val id: String,
    val title: String,
    val description: String,
    val startDate: Date,
    val endDate: Date? = null,
    val location: Location,
    val organizer: String,
    val categoryId: String,
    val categoryName: String,
    val imageUrl: String? = null,
    val price: Double? = null,
    val isFree: Boolean = false,
    val website: String? = null,
    val contactInfo: String? = null,
    val isFavorite: Boolean = false
)

/**
 * Data class representing a location with a name and coordinates.
 */
data class Location(
    val name: String,
    val address: String,
    val city: String,
    val latitude: Double? = null,
    val longitude: Double? = null
)

/**
 * Data class representing an event category.
 */
data class Category(
    val id: String,
    val name: String,
    val description: String? = null,
    val imageUrl: String? = null,
    val colorResId: Int? = null
)

// Explanation for junior developers:
// 1. Data classes in Kotlin automatically provide useful functionality:
//    - equals()/hashCode() for comparing objects
//    - toString() for debugging
//    - copy() for creating modified copies of objects
//    - componentN() functions for destructuring
//
// 2. Default parameter values (like endDate = null) make fields optional
//
// 3. Nullable types (marked with ?) indicate that a value can be null
//
// 4. The domain models represent the core business entities and are independent
//    of any external frameworks or implementation details
//
// 5. We use domain models in the business logic layer, keeping them separate from
//    database entities or network DTOs for better separation of concerns 
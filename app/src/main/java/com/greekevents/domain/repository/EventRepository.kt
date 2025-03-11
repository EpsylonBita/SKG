package com.greekevents.domain.repository

import com.greekevents.domain.model.Category
import com.greekevents.domain.model.Event
import kotlinx.coroutines.flow.Flow
import java.util.Date

/**
 * Repository interface for accessing and managing event data.
 * 
 * This interface defines methods for retrieving, searching, and filtering events.
 * It abstracts the data source from the rest of the application.
 */
interface EventRepository {

    /**
     * Get a single event by its ID.
     * 
     * @param eventId The unique identifier of the event
     * @return Flow emitting the event or null if not found
     */
    suspend fun getEventById(eventId: String): Event?
    
    /**
     * Get all events.
     * 
     * @return Flow emitting a list of all events
     */
    fun getAllEvents(): Flow<List<Event>>
    
    /**
     * Get events based on search criteria.
     * 
     * @param query Search query for event title or description
     * @param location Optional location to filter events
     * @param startDate Optional start date to filter events
     * @param endDate Optional end date to filter events
     * @param categoryId Optional category ID to filter events
     * @return Flow emitting a list of matching events
     */
    fun searchEvents(
        query: String,
        location: String? = null,
        startDate: Date? = null,
        endDate: Date? = null,
        categoryId: String? = null
    ): Flow<List<Event>>
    
    /**
     * Get events for a specific category.
     * 
     * @param categoryId The unique identifier of the category
     * @return Flow emitting a list of events in the category
     */
    fun getEventsByCategory(categoryId: String): Flow<List<Event>>
    
    /**
     * Get events near a specific location.
     * 
     * @param latitude Latitude coordinate
     * @param longitude Longitude coordinate
     * @param radiusKm Radius in kilometers to search within
     * @return Flow emitting a list of nearby events
     */
    fun getNearbyEvents(latitude: Double, longitude: Double, radiusKm: Double = 10.0): Flow<List<Event>>
    
    /**
     * Get events happening within a date range.
     * 
     * @param startDate Start date of the range
     * @param endDate End date of the range
     * @return Flow emitting a list of events in the date range
     */
    fun getEventsByDateRange(startDate: Date, endDate: Date): Flow<List<Event>>
    
    /**
     * Get popular events based on views or registrations.
     * 
     * @param limit Maximum number of events to return
     * @return Flow emitting a list of popular events
     */
    fun getPopularEvents(limit: Int = 10): Flow<List<Event>>
    
    /**
     * Get all available event categories.
     * 
     * @return Flow emitting a list of all categories
     */
    fun getAllCategories(): Flow<List<Category>>
    
    /**
     * Get a single category by its ID.
     * 
     * @param categoryId The unique identifier of the category
     * @return Flow emitting the category or null if not found
     */
    suspend fun getCategoryById(categoryId: String): Category?
}

// Explanation for junior developers:
// 1. This is a repository interface, part of the domain layer in Clean Architecture
//    - It defines a contract for data operations without implementation details
//    - It allows for easy testing and swapping of data sources
//
// 2. The methods return Flow<T> from Kotlin coroutines
//    - Flow is a reactive stream that can emit multiple values over time
//    - It's perfect for observing data that might change
//
// 3. suspend functions are coroutine-compatible
//    - They can be paused and resumed without blocking threads
//    - This makes them efficient for network or database operations
//
// 4. The interface focuses on the "what" not the "how"
//    - How data is fetched (network, database, etc.) is an implementation detail
//    - The domain layer only cares about what data operations are available
//
// 5. Default parameter values provide convenience for optional filters
//    - For example, getNearbyEvents has a default radius of 10km 
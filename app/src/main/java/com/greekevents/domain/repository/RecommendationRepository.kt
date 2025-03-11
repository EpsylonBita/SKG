package com.greekevents.domain.repository

import com.greekevents.domain.model.Event
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for accessing personalized event recommendations.
 * 
 * This interface defines methods for retrieving recommendations based on user behavior
 * and preferences. The implementation will use algorithms to analyze user history
 * and suggest relevant events.
 */
interface RecommendationRepository {

    /**
     * Get personalized event recommendations for the current user.
     * 
     * @param limit Maximum number of recommendations to return
     * @return Flow emitting a list of recommended events
     */
    fun getPersonalizedRecommendations(limit: Int = 10): Flow<List<Event>>
    
    /**
     * Get event recommendations based on a specific event.
     * 
     * @param eventId ID of the event to base recommendations on
     * @param limit Maximum number of recommendations to return
     * @return Flow emitting a list of similar events
     */
    fun getSimilarEvents(eventId: String, limit: Int = 5): Flow<List<Event>>
    
    /**
     * Get event recommendations based on categories the user has interacted with.
     * 
     * @param limit Maximum number of recommendations to return
     * @return Flow emitting a list of events from preferred categories
     */
    fun getRecommendationsByCategory(limit: Int = 10): Flow<List<Event>>
    
    /**
     * Get event recommendations based on the user's location.
     * 
     * @param latitude User's latitude
     * @param longitude User's longitude
     * @param radiusKm Radius in kilometers to search within
     * @param limit Maximum number of recommendations to return
     * @return Flow emitting a list of nearby recommended events
     */
    fun getLocationBasedRecommendations(
        latitude: Double,
        longitude: Double,
        radiusKm: Double = 10.0,
        limit: Int = 10
    ): Flow<List<Event>>
    
    /**
     * Get recommendations for trending or popular events.
     * 
     * @param limit Maximum number of recommendations to return
     * @return Flow emitting a list of trending events
     */
    fun getTrendingEvents(limit: Int = 10): Flow<List<Event>>
    
    /**
     * Get recommendations for upcoming events within a time period.
     * 
     * @param daysAhead Number of days ahead to look for events
     * @param limit Maximum number of recommendations to return
     * @return Flow emitting a list of upcoming events
     */
    fun getUpcomingEvents(daysAhead: Int = 7, limit: Int = 10): Flow<List<Event>>
}

// Explanation for junior developers:
// 1. The RecommendationRepository focuses solely on providing personalized recommendations
//    - It's a specialized repository with a single responsibility
//    - This separation keeps the code modular and easier to maintain
//
// 2. Various recommendation types are supported:
//    - Personalized recommendations based on overall user behavior
//    - Similar events to what the user has viewed
//    - Category-based recommendations
//    - Location-based recommendations
//    - Trending events (popular with all users)
//    - Upcoming events (time-based recommendations)
//
// 3. Flow<List<Event>> is used for all methods because:
//    - Recommendations can change as the user interacts with the app
//    - The UI can observe and update automatically when recommendations change
//
// 4. Default parameters provide sensible defaults:
//    - For example, limit parameters default to reasonable values
//    - Location search radius defaults to 10km
//    - Upcoming events defaults to 7 days ahead
//
// 5. The implementation will use algorithms to analyze user history:
//    - Viewed events
//    - Search history
//    - Favorites
//    - Explicit preferences 
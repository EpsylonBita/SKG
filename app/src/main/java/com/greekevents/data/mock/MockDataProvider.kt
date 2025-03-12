package com.greekevents.data.mock

import com.greekevents.R
import com.greekevents.domain.model.Category
import com.greekevents.domain.model.Event
import com.greekevents.domain.model.Location
import java.util.Date
import java.util.UUID
import java.util.concurrent.TimeUnit
import kotlin.random.Random

/**
 * Provides mock data for the application when real data is not available.
 * This is useful for development, testing, and demo purposes.
 */
object MockDataProvider {

    /**
     * Get a list of mock events with high-quality Unsplash images.
     */
    fun getMockEvents(): List<Event> {
        val currentDate = Date()
        
        return listOf(
            Event(
                id = "1",
                title = "Athens Music Festival",
                description = "Experience the best of Greek and international music at this annual festival featuring top artists from around the world.",
                startDate = Date(currentDate.time + TimeUnit.DAYS.toMillis(2)),
                endDate = Date(currentDate.time + TimeUnit.DAYS.toMillis(4)),
                location = Location(
                    name = "Athens Concert Hall",
                    address = "Vasilissis Sofias Ave. 115 21",
                    city = "Athens",
                    latitude = 37.9838,
                    longitude = 23.7275
                ),
                organizer = "Athens Cultural Organization",
                categoryId = "music",
                categoryName = "Music",
                imageUrl = "https://images.unsplash.com/photo-1470229722913-7c0e2dbbafd3?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1170&q=80",
                price = 25.0,
                isFree = false,
                website = "https://example.com/athens-music-festival",
                contactInfo = "info@athensmusicfestival.gr",
                isFavorite = false
            ),
            Event(
                id = "2",
                title = "Thessaloniki Food Festival",
                description = "Taste the flavors of Greece at this culinary celebration featuring local chefs, traditional dishes, and modern Greek cuisine.",
                startDate = Date(currentDate.time + TimeUnit.DAYS.toMillis(5)),
                endDate = Date(currentDate.time + TimeUnit.DAYS.toMillis(7)),
                location = Location(
                    name = "Aristotelous Square",
                    address = "Aristotelous Square",
                    city = "Thessaloniki",
                    latitude = 40.6333,
                    longitude = 22.9500
                ),
                organizer = "Thessaloniki Culinary Association",
                categoryId = "food",
                categoryName = "Food",
                imageUrl = "https://images.unsplash.com/photo-1555939594-58d7cb561ad1?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=687&q=80",
                price = 15.0,
                isFree = false,
                website = "https://example.com/thessaloniki-food-festival",
                contactInfo = "info@thessalonikifoodfestival.gr",
                isFavorite = true
            ),
            Event(
                id = "3",
                title = "Santorini Art Exhibition",
                description = "Discover the works of contemporary Greek artists against the stunning backdrop of Santorini's iconic landscape.",
                startDate = Date(currentDate.time + TimeUnit.DAYS.toMillis(10)),
                endDate = Date(currentDate.time + TimeUnit.DAYS.toMillis(20)),
                location = Location(
                    name = "Santorini Arts Center",
                    address = "Oia",
                    city = "Santorini",
                    latitude = 36.4618,
                    longitude = 25.3760
                ),
                organizer = "Cyclades Art Foundation",
                categoryId = "art",
                categoryName = "Art",
                imageUrl = "https://images.unsplash.com/photo-1513735492246-483525079686?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1169&q=80",
                price = 10.0,
                isFree = false,
                website = "https://example.com/santorini-art-exhibition",
                contactInfo = "info@cycladesart.gr",
                isFavorite = false
            ),
            Event(
                id = "4",
                title = "Olympia Sports Tournament",
                description = "Compete in various athletic events at the birthplace of the Olympic Games in this modern revival of ancient traditions.",
                startDate = Date(currentDate.time + TimeUnit.DAYS.toMillis(15)),
                endDate = Date(currentDate.time + TimeUnit.DAYS.toMillis(17)),
                location = Location(
                    name = "Ancient Olympia",
                    address = "Ancient Olympia",
                    city = "Olympia",
                    latitude = 37.6379,
                    longitude = 21.6302
                ),
                organizer = "Hellenic Athletic Association",
                categoryId = "sports",
                categoryName = "Sports",
                imageUrl = "https://images.unsplash.com/photo-1517649763962-0c623066013b?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1170&q=80",
                price = 20.0,
                isFree = false,
                website = "https://example.com/olympia-sports-tournament",
                contactInfo = "info@hellenicathletics.gr",
                isFavorite = false
            ),
            Event(
                id = "5",
                title = "Delphi Cultural Festival",
                description = "Celebrate Greek heritage with performances, exhibitions, and workshops at the ancient site of Delphi.",
                startDate = Date(currentDate.time + TimeUnit.DAYS.toMillis(20)),
                endDate = Date(currentDate.time + TimeUnit.DAYS.toMillis(25)),
                location = Location(
                    name = "Delphi Archaeological Site",
                    address = "Delphi",
                    city = "Delphi",
                    latitude = 38.4824,
                    longitude = 22.5010
                ),
                organizer = "Delphi Cultural Foundation",
                categoryId = "culture",
                categoryName = "Culture",
                imageUrl = "https://images.unsplash.com/photo-1608730973965-1d0a1b6b8c98?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1170&q=80",
                price = 0.0,
                isFree = true,
                website = "https://example.com/delphi-cultural-festival",
                contactInfo = "info@delphiculture.gr",
                isFavorite = true
            ),
            Event(
                id = "6",
                title = "Athens Tech Conference",
                description = "Join tech leaders and innovators for discussions on the latest trends and technologies shaping the future.",
                startDate = Date(currentDate.time + TimeUnit.DAYS.toMillis(30)),
                endDate = Date(currentDate.time + TimeUnit.DAYS.toMillis(32)),
                location = Location(
                    name = "Athens International Conference Center",
                    address = "Mesogeion Avenue 432",
                    city = "Athens",
                    latitude = 37.9908,
                    longitude = 23.7383
                ),
                organizer = "Greek Tech Association",
                categoryId = "education",
                categoryName = "Education",
                imageUrl = "https://images.unsplash.com/photo-1540575467063-178a50c2df87?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1170&q=80",
                price = 50.0,
                isFree = false,
                website = "https://example.com/athens-tech-conference",
                contactInfo = "info@greektech.gr",
                isFavorite = false
            )
        )
    }

    /**
     * Get a list of mock categories with high-quality Unsplash images.
     */
    fun getMockCategories(): List<Category> {
        return listOf(
            Category(
                id = "music",
                name = "Music",
                description = "Live music events and concerts",
                imageUrl = "https://images.unsplash.com/photo-1511671782779-c97d3d27a1d4?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1170&q=80",
                colorResId = R.color.category_music
            ),
            Category(
                id = "sports",
                name = "Sports",
                description = "Sports events and tournaments",
                imageUrl = "https://images.unsplash.com/photo-1461896836934-ffe607ba8211?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1170&q=80",
                colorResId = R.color.category_sports
            ),
            Category(
                id = "food",
                name = "Food",
                description = "Food festivals and culinary events",
                imageUrl = "https://images.unsplash.com/photo-1504674900247-0877df9cc836?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1170&q=80",
                colorResId = R.color.category_food
            ),
            Category(
                id = "art",
                name = "Art",
                description = "Art exhibitions and galleries",
                imageUrl = "https://images.unsplash.com/photo-1579783902614-a3fb3927b6a5?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=745&q=80",
                colorResId = R.color.category_art
            ),
            Category(
                id = "culture",
                name = "Culture",
                description = "Cultural events and festivals",
                imageUrl = "https://images.unsplash.com/photo-1566140967404-b8b3932483f5?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1170&q=80",
                colorResId = R.color.category_culture
            ),
            Category(
                id = "education",
                name = "Education",
                description = "Educational workshops and seminars",
                imageUrl = "https://images.unsplash.com/photo-1523050854058-8df90110c9f1?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1170&q=80",
                colorResId = R.color.category_education
            )
        )
    }

    /**
     * Generate a random event for testing purposes.
     */
    fun generateRandomEvent(): Event {
        val categories = getMockCategories()
        val randomCategory = categories.random()
        val currentDate = Date()
        
        return Event(
            id = UUID.randomUUID().toString(),
            title = "Random Event ${Random.nextInt(1, 101)}",
            description = "This is a randomly generated event for testing purposes.",
            startDate = Date(currentDate.time + TimeUnit.DAYS.toMillis(Random.nextInt(1, 31).toLong())),
            location = Location(
                name = "Random Venue",
                address = "Random Street 123",
                city = "Athens",
                latitude = 37.9838 + Random.nextDouble(-0.1, 0.1),
                longitude = 23.7275 + Random.nextDouble(-0.1, 0.1)
            ),
            organizer = "Test Organizer",
            categoryId = randomCategory.id,
            categoryName = randomCategory.name,
            imageUrl = randomCategory.imageUrl,
            price = if (Random.nextInt(0, 2) == 0) Random.nextInt(5, 51).toDouble() else null,
            isFree = Random.nextInt(0, 2) == 0,
            isFavorite = Random.nextInt(0, 2) == 0
        )
    }
} 
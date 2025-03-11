package com.greekevents.di

import com.greekevents.data.repository.EventRepositoryImpl
import com.greekevents.data.repository.RecommendationRepositoryImpl
import com.greekevents.data.repository.UserRepositoryImpl
import com.greekevents.domain.repository.EventRepository
import com.greekevents.domain.repository.RecommendationRepository
import com.greekevents.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Hilt module that provides repository implementations as singletons.
 * 
 * This module binds the repository interfaces to their implementations,
 * allowing Hilt to inject them wherever they are needed in the app.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    /**
     * Binds the EventRepository interface to its implementation.
     * 
     * @param impl The EventRepositoryImpl instance
     * @return The bound EventRepository
     */
    @Binds
    @Singleton
    abstract fun bindEventRepository(impl: EventRepositoryImpl): EventRepository

    /**
     * Binds the UserRepository interface to its implementation.
     * 
     * @param impl The UserRepositoryImpl instance
     * @return The bound UserRepository
     */
    @Binds
    @Singleton
    abstract fun bindUserRepository(impl: UserRepositoryImpl): UserRepository

    /**
     * Binds the RecommendationRepository interface to its implementation.
     * 
     * @param impl The RecommendationRepositoryImpl instance
     * @return The bound RecommendationRepository
     */
    @Binds
    @Singleton
    abstract fun bindRecommendationRepository(impl: RecommendationRepositoryImpl): RecommendationRepository
}

// Explanation for junior developers:
// 1. @Module annotation tells Hilt that this class provides dependencies
//
// 2. @InstallIn(SingletonComponent::class) means these dependencies will live
//    as long as the Application does - they are singletons
//
// 3. @Binds is used to tell Hilt which implementation to use for an interface
//    - It's more efficient than @Provides for this simple binding case
//    - The parameter is the implementation and the return type is the interface
//
// 4. @Singleton ensures only one instance of each repository is created
//    - This is important for repositories that might maintain caches or connections
//
// 5. The abstract class with abstract methods is the standard pattern for @Binds
//    - Hilt generates the actual implementation at compile time
//    - The abstract methods tell Hilt how to resolve dependencies
//
// 6. This module doesn't create the repository implementations directly
//    - It just tells Hilt which implementation to use for each interface
//    - The implementations themselves have their own dependencies injected by Hilt 
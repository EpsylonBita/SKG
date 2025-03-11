package com.greekevents

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * The main Application class for the Greek Events app.
 * 
 * This class is the entry point of our application and is responsible for
 * initializing app-wide components and libraries.
 * 
 * The @HiltAndroidApp annotation generates all the necessary Dagger components
 * for dependency injection. This creates an Application-level component that
 * will be the parent component of the dependency graph.
 */
@HiltAndroidApp
class GreekEventsApplication : Application() {
    
    override fun onCreate() {
        super.onCreate()
        // Initialize any app-wide libraries or configurations here
        // For example, we might initialize crash reporting tools, analytics,
        // or other libraries that need application context.
    }
}

// Explanation for junior developers:
// 1. The Application class is a base class that maintains global application state
// 2. It's instantiated before any other class when the app starts
// 3. @HiltAndroidApp is an annotation that triggers Hilt's code generation
// 4. Hilt is a dependency injection library that provides a standard way to incorporate
//    dependency injection into an Android application
// 5. By using Hilt, we can easily manage dependencies throughout the app
// 6. The onCreate() method is called when the application is starting, before any
//    Activity, Service, or Receiver objects have been created 
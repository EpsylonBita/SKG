# Greek Events Discovery App

An Android application developed in Kotlin that allows users to discover events across Greece.

## Features

- **Event Search**: Search for events using keywords, locations, or date ranges
- **Categorization**: Browse events by categories (music, sports, culture, art, etc.)
- **User Authentication**: Create accounts, log in securely, and manage profiles
- **Personalization**: Track user history for personalized recommendations
- **Recommendation Algorithm**: Get event suggestions based on preferences and behavior
- **Modern UI**: Intuitive navigation with Material Design principles

## Project Structure

```
app/
├── src/
│   ├── main/
│   │   ├── java/com/greekevents/
│   │   │   ├── data/           # Data layer (repositories, data sources)
│   │   │   ├── di/             # Dependency injection
│   │   │   ├── domain/         # Domain layer (use cases, business logic)
│   │   │   ├── presentation/   # UI layer (activities, fragments, viewmodels)
│   │   │   ├── utils/          # Utility classes
│   │   │   └── MainActivity.kt # Main entry point
│   │   ├── res/                # Resources (layouts, drawables, values)
│   │   └── AndroidManifest.xml
│   └── test/                   # Unit and instrumentation tests
├── build.gradle                # Project-level build file
└── app/build.gradle            # App-level build file
```

## Architecture

This application follows the MVVM (Model-View-ViewModel) architecture pattern with Clean Architecture principles:

- **Presentation Layer**: Activities, Fragments, ViewModels
- **Domain Layer**: Use Cases, Business Logic
- **Data Layer**: Repositories, Remote/Local Data Sources

## Technologies

- Kotlin
- Jetpack Components (ViewModel, LiveData, Room)
- Coroutines for asynchronous operations
- Retrofit for API communication
- Material Design components
- Firebase for authentication
- Dagger Hilt for dependency injection
- Navigation Component for navigation

## Educational Purpose

This project includes detailed code explanations for junior Android developers to understand:
- Modern Android development practices
- Kotlin programming concepts
- Architecture patterns and principles
- UI/UX design implementation 
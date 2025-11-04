package com.jetbrains.kmpapp.di

import com.jetbrains.kmpapp.data.RickAndMortyApi
import com.jetbrains.kmpapp.data.RickAndMortyRepository
import com.jetbrains.kmpapp.screens.detail.DetailViewModel
import com.jetbrains.kmpapp.screens.list.ListViewModel
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

/**
 * Koin module for data layer dependencies
 * Provides singleton instances of HTTP client, API, and repository
 */
val dataModule = module {
    // Provide a singleton HttpClient configured for JSON
    single {
        // Configure JSON to ignore unknown keys (API might have extra fields)
        val json = Json { ignoreUnknownKeys = true }

        // Create HttpClient with content negotiation for JSON
        HttpClient {
            install(ContentNegotiation) {
                // Configure JSON serialization/deserialization
                json(json, contentType = ContentType.Application.Json)
            }
        }
    }

    // Provide singleton instance of RickAndMortyApi
    // get() automatically resolves HttpClient dependency
    single { RickAndMortyApi(get()) }

    // Provide singleton instance of RickAndMortyRepository
    // get() automatically resolves RickAndMortyApi dependency
    single { RickAndMortyRepository(get()) }
}

/**
 * Koin module for ViewModel dependencies
 * ViewModels are created as factories (new instance each time)
 */
val viewModelModule = module {
    // Factory for ListViewModel - creates new instance when requested
    factoryOf(::ListViewModel)

    // Factory for DetailViewModel - creates new instance when requested
    factoryOf(::DetailViewModel)
}

/**
 * Initialize Koin dependency injection
 * Should be called once at app startup
 */
fun initKoin() {
    startKoin {
        modules(
            dataModule,      // Register data layer dependencies
            viewModelModule, // Register ViewModel dependencies
        )
    }
}


package com.jetbrains.kmpapp.data

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

/**
 * API client for the Rick & Morty API
 * Handles HTTP requests to fetch character data
 */
class RickAndMortyApi(private val client: HttpClient) {
    companion object {
        // Base URL for all API endpoints
        private const val BASE_URL = "https://rickandmortyapi.com/api"
    }

    /**
     * Fetches a paginated list of characters from the API
     * @param page The page number to fetch (default is 1)
     * @return RickAndMortyResponse containing info and list of characters
     */
    suspend fun getCharacters(page: Int = 1): RickAndMortyResponse {
        // Make GET request to the character endpoint with page parameter
        // .body() automatically deserializes JSON to RickAndMortyResponse
        return client.get("$BASE_URL/character/?page=$page").body()
    }

    /**
     * Fetches a single character by ID from the API
     * @param id The character ID to fetch
     * @return RickAndMortyCharacter object with character details
     */
    suspend fun getCharacterById(id: Int): RickAndMortyCharacter {
        // Make GET request to the character endpoint with specific ID
        // .body() automatically deserializes JSON to RickAndMortyCharacter
        return client.get("$BASE_URL/character/$id").body()
    }
}


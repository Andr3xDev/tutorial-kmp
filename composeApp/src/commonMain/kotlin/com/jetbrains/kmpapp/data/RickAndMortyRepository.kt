package com.jetbrains.kmpapp.data

/**
 * Repository that abstracts the data source for Rick & Morty characters
 * Provides a clean API for ViewModels to fetch character data
 * Handles error cases and data transformation
 */
class RickAndMortyRepository(private val api: RickAndMortyApi) {

    /**
     * Fetches a list of characters, limited to 15 items
     * @return List of RickAndMortyCharacter objects, or empty list on error
     */
    suspend fun getCharacters(): List<RickAndMortyCharacter> {
        return try {
            // Call the API to get the first page of characters
            val response = api.getCharacters(page = 1)

            // Take only the first 15 characters from the results
            response.results.take(15)
        } catch (e: Exception) {
            // Log error for debugging
            e.printStackTrace()

            // Return empty list instead of crashing the app
            emptyList()
        }
    }

    /**
     * Fetches a single character by ID
     * @param id The character ID to fetch
     * @return RickAndMortyCharacter object, or null if not found or error occurs
     */
    suspend fun getCharacterById(id: Int): RickAndMortyCharacter? {
        return try {
            // Call the API to get the specific character
            api.getCharacterById(id)
        } catch (e: Exception) {
            // Log error for debugging
            e.printStackTrace()

            // Return null instead of crashing the app
            null
        }
    }
}


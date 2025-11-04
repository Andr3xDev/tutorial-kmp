package com.jetbrains.kmpapp.data

class RickAndMortyRepository(private val api: RickAndMortyApi) {

    suspend fun getCharacters(): List<RickAndMortyCharacter> {
        return try {
            val response = api.getCharacters(page = 1)
            response.results.take(15) // Tomamos solo los primeros 15 personajes
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    suspend fun getCharacterById(id: Int): RickAndMortyCharacter? {
        return try {
            api.getCharacterById(id)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}


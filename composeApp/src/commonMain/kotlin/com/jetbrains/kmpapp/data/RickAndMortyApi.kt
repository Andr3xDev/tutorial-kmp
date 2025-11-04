
package com.jetbrains.kmpapp.data

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class RickAndMortyApi(private val client: HttpClient) {
    companion object {
        private const val BASE_URL = "https://rickandmortyapi.com/api"
    }

    suspend fun getCharacters(page: Int = 1): RickAndMortyResponse {
        return client.get("$BASE_URL/character/?page=$page").body()
    }

    suspend fun getCharacterById(id: Int): RickAndMortyCharacter {
        return client.get("$BASE_URL/character/$id").body()
    }
}


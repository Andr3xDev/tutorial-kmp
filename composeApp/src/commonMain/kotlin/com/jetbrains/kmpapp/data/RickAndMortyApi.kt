package com.jetbrains.kmpapp.data

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

// PARTE 2: Implementar llamadas a la API de Rick and Morty
// Objetivo: realizar peticiones HTTP y mapear las respuestas a objetos Kotlin
class RickAndMortyApi(private val client: HttpClient) {
    companion object {
        private const val BASE_URL = "https://rickandmortyapi.com/api"
    }

    // TODO: Implementa esta función para obtener la lista paginada de personajes
    // Endpoint: GET $BASE_URL/character/?page={page}
    // Retorna: RickAndMortyResponse con la lista de personajes e info de paginación
    suspend fun getCharacters(page: Int = 1): RickAndMortyResponse? {
        // Reemplaza este return null con:
        // return client.get("$BASE_URL/character/?page=$page").body()
        return null
    }

    // TODO: Implementa esta función para obtener el detalle de un personaje por ID
    // Endpoint: GET $BASE_URL/character/{id}
    // Retorna: RickAndMortyCharacter con toda la información del personaje
    suspend fun getCharacterById(id: Int): RickAndMortyCharacter? {
        // Reemplaza este return null con:
        // return client.get("$BASE_URL/character/$id").body()
        return null
    }
}
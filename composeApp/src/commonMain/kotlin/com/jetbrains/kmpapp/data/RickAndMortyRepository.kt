package com.jetbrains.kmpapp.data

// PARTE 2: Implementar Repository con manejo de errores
// Objetivo: usar la API y manejar excepciones para retornar datos seguros
class RickAndMortyRepository(private val api: RickAndMortyApi) {

    // TODO: Implementa esta función para obtener la lista de personajes
    // Debe: llamar api.getCharacters(), manejar errores con try/catch, limitar a 15 items
    suspend fun getCharacters(): List<RickAndMortyCharacter> {
        // Reemplaza este return emptyList() con:
        // return try {
        //     val response = api.getCharacters(page = 1)
        //     response?.results?.take(15) ?: emptyList()
        // } catch (e: Exception) {
        //     e.printStackTrace()
        //     emptyList()
        // }
        return emptyList()
    }

    // TODO: Implementa esta función para obtener un personaje por ID
    // Debe: llamar api.getCharacterById(id), manejar errores, retornar null si falla
    suspend fun getCharacterById(id: Int): RickAndMortyCharacter? {
        // Reemplaza este return null con:
        // return try {
        //     api.getCharacterById(id)
        // } catch (e: Exception) {
        //     e.printStackTrace()
        //     null
        // }
        return null
    }
}


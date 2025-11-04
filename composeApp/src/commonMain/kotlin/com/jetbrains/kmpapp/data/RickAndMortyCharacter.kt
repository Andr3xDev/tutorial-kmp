package com.jetbrains.kmpapp.data

import kotlinx.serialization.Serializable

/**
 * Response wrapper from the Rick & Morty API
 * Contains pagination info and the list of characters
 */
@Serializable
data class RickAndMortyResponse(
    val info: Info,                              // Pagination and metadata
    val results: List<RickAndMortyCharacter>     // List of characters in this page
)

/**
 * Pagination information from the API
 */
@Serializable
data class Info(
    val count: Int,           // Total number of characters in the database
    val pages: Int,           // Total number of pages
    val next: String? = null, // URL for the next page (null if last page)
    val prev: String? = null  // URL for the previous page (null if first page)
)

/**
 * Main character model representing a Rick & Morty character
 * All properties match the JSON structure from the API
 */
@Serializable
data class RickAndMortyCharacter(
    val id: Int,                    // Unique character ID
    val name: String,               // Character name
    val status: String,             // Alive, Dead, or Unknown
    val species: String,            // Human, Alien, etc.
    val type: String = "",          // Type or subspecies (optional)
    val gender: String,             // Male, Female, Genderless, or Unknown
    val origin: Location,           // Character's origin location
    val location: Location,         // Character's last known location
    val image: String,              // URL to character's image
    val episode: List<String>,      // List of episode URLs where character appears
    val url: String,                // URL to character's own API endpoint
    val created: String             // Timestamp when character was created in the database
)

/**
 * Location model used for origin and current location
 */
@Serializable
data class Location(
    val name: String,  // Name of the location
    val url: String    // URL to location's API endpoint
)


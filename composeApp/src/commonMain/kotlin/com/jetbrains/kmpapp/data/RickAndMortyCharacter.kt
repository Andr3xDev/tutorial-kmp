package com.jetbrains.kmpapp.data

import kotlinx.serialization.Serializable

// TODO PARTE 1: Define los modelos de datos con @Serializable

@Serializable
data class RickAndMortyResponse(
    val info: Info,
    val results: List<RickAndMortyCharacter>
)

@Serializable
data class Info(
    val count: Int,
    val pages: Int,
    val next: String? = null,
    val prev: String? = null
)

@Serializable
data class RickAndMortyCharacter(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val type: String = "",
    val gender: String,
    val origin: Location,
    val location: Location,
    val image: String,
    val episode: List<String>,
    val url: String,
    val created: String
)

@Serializable
data class Location(
    val name: String,
    val url: String
)

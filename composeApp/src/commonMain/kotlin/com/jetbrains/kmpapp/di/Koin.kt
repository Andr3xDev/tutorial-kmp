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

// TODO PARTE 2: Configura los módulos de Koin

val dataModule = module {
    // TODO: Define HttpClient como single con ContentNegotiation
    single {
        val json = Json { ignoreUnknownKeys = true }
        HttpClient {
            install(ContentNegotiation) {
                json(json, contentType = ContentType.Application.Json)
            }
        }
    }

    // TODO: Define Api y Repository como single
    single { RickAndMortyApi(get()) }
    single { RickAndMortyRepository(get()) }
}

val viewModelModule = module {
    // TODO: Define ViewModels como factory
    factoryOf(::ListViewModel)
    factoryOf(::DetailViewModel)
}

fun initKoin() {
    startKoin {
        modules(dataModule, viewModelModule)
    }
}

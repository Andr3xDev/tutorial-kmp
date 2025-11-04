package com.jetbrains.kmpapp.screens.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jetbrains.kmpapp.data.RickAndMortyCharacter
import com.jetbrains.kmpapp.data.RickAndMortyRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// TODO PARTE 2: Implementa DetailViewModel (similar a ListViewModel)
class DetailViewModel(private val rickAndMortyRepository: RickAndMortyRepository) : ViewModel() {

    // TODO: Define StateFlow para character (nullable)
    private val _character = MutableStateFlow<RickAndMortyCharacter?>(null)
    val character: StateFlow<RickAndMortyCharacter?> = _character.asStateFlow()

    // TODO: Define StateFlow para isLoading
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    // TODO: Implementa loadCharacter(id) - NO se llama en init, recibe parámetro
    fun loadCharacter(characterId: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            val result = rickAndMortyRepository.getCharacterById(characterId)
            _character.value = result
            _isLoading.value = false
        }
    }
}
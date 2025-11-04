package com.jetbrains.kmpapp.screens.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jetbrains.kmpapp.data.RickAndMortyCharacter
import com.jetbrains.kmpapp.data.RickAndMortyRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// TODO PARTE 2: Implementa la lógica del ViewModel
class ListViewModel(private val rickAndMortyRepository: RickAndMortyRepository) : ViewModel() {

    // TODO: Define StateFlow para characters (backing property pattern)
    private val _characters = MutableStateFlow<List<RickAndMortyCharacter>>(emptyList())
    val characters: StateFlow<List<RickAndMortyCharacter>> = _characters.asStateFlow()

    // TODO: Define StateFlow para isLoading
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        loadCharacters()
    }

    // TODO: Implementa loadCharacters() - usa viewModelScope.launch
    fun loadCharacters() {
        viewModelScope.launch {
            _isLoading.value = true
            val result = rickAndMortyRepository.getCharacters()
            _characters.value = result
            _isLoading.value = false
        }
    }
}

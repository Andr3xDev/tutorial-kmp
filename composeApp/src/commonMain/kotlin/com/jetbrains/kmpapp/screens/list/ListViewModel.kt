package com.jetbrains.kmpapp.screens.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jetbrains.kmpapp.data.RickAndMortyCharacter
import com.jetbrains.kmpapp.data.RickAndMortyRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ListViewModel(private val rickAndMortyRepository: RickAndMortyRepository) : ViewModel() {
    private val _characters = MutableStateFlow<List<RickAndMortyCharacter>>(emptyList())
    val characters: StateFlow<List<RickAndMortyCharacter>> = _characters.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        loadCharacters()
    }

    fun loadCharacters() {
        viewModelScope.launch {
            _isLoading.value = true
            val result = rickAndMortyRepository.getCharacters()
            _characters.value = result
            _isLoading.value = false
        }
    }
}

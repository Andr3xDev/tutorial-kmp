package com.jetbrains.kmpapp.screens.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jetbrains.kmpapp.data.RickAndMortyCharacter
import com.jetbrains.kmpapp.data.RickAndMortyRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel for the character list screen
 * Manages the state and business logic for displaying Rick & Morty characters
 */
class ListViewModel(private val rickAndMortyRepository: RickAndMortyRepository) : ViewModel() {
    // Private mutable state for characters list - only modifiable within this ViewModel
    private val _characters = MutableStateFlow<List<RickAndMortyCharacter>>(emptyList())
    // Public read-only state that UI can observe
    val characters: StateFlow<List<RickAndMortyCharacter>> = _characters.asStateFlow()

    // Private mutable state for loading indicator
    private val _isLoading = MutableStateFlow(false)
    // Public read-only state for loading status
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        // Load characters automatically when ViewModel is created
        loadCharacters()
    }

    /**
     * Loads characters from the repository
     * Updates the loading state and characters list
     */
    fun loadCharacters() {
        // Launch a coroutine in the ViewModel scope (lifecycle-aware)
        viewModelScope.launch {
            // Show loading indicator
            _isLoading.value = true

            // Fetch characters from repository (suspend function, runs asynchronously)
            val result = rickAndMortyRepository.getCharacters()

            // Update the characters state with fetched data
            _characters.value = result

            // Hide loading indicator
            _isLoading.value = false
        }
    }
}
